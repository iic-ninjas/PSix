package com.paypal.psix.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.paypal.psix.R;
import com.paypal.psix.activities.EventStatusActivity;
import com.paypal.psix.activities.SetupEventActivity;
import com.paypal.psix.adapters.EventsAdapter;
import com.paypal.psix.models.Event;
import com.paypal.psix.services.PSixEventsSyncService;
import com.paypal.psix.utils.BusProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shay on 3/3/15.
 */
public class EventsFragment extends Fragment implements PSixEventsSyncService.EventsSyncCallback {

    private static final String LOG_TAG = "Events";

    @InjectView(R.id.events_list_view) ListView listView;

    EventsAdapter adapter;
    ProgressDialog progress;
    ArrayList<Event> data = new ArrayList<>();
    Bus bus = BusProvider.getInstance();

    public EventsFragment() { }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.inject(this, rootView);

        refreshDataSource();
        adapter = new EventsAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event event = (Event) adapterView.getItemAtPosition(position);
                if (event != null) navigateToEvent(event);
            }
        });

        bus.register(this);
        sync();

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        bus.unregister(this);
    }

    private void navigateToEvent(Event event) {
        Class<?> klass = event.hasSetup ? EventStatusActivity.class : SetupEventActivity.class;
        Intent intent = new Intent(getActivity(), klass);
        intent.putExtra(Event.TAG, event.getId());
        startActivity(intent);
    }

    private void refreshDataSource() {
        data.clear();
        List<Event> list = new Select().from(Event.class).execute();
        data.addAll(list);

        Collections.sort(data, new Comparator<Event>() {
            public int compare(Event e1, Event e2) {
                if (e1.hasSetup && e2.hasSetup || !e1.hasSetup && !e2.hasSetup) {
                    return (e1.timestamp > e2.timestamp) ? 1 : -1;
                } else if (!e1.hasSetup && e2.hasSetup) {
                    return 1;
                } else if (e1.hasSetup && !e2.hasSetup) {
                    return -1;
                } else return (e1.timestamp > e2.timestamp) ? 1 : -1;
            }
        });
    }

    private void sync() {
        if (data.isEmpty()) {
            progress = ProgressDialog.show(getActivity(), getString(R.string.please_wait), getString(R.string.fetching_events));
        }
        PSixEventsSyncService.syncFacebookEvent(this);
    }

    @Override
    public void eventsSyncedCallback() {
        if (progress != null) progress.dismiss();
        refreshDataSource();
        adapter.notifyDataSetChanged();
    }


    @Subscribe
    public void answerAvailable(SetupEventFragment.SuccessNotification event) {
        eventsSyncedCallback();
    }

}