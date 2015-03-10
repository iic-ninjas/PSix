package com.paypal.psix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.paypal.psix.R;
import com.paypal.psix.activities.EventStatusActivity;
import com.paypal.psix.activities.SetupEventActivity;
import com.paypal.psix.adapters.EventsAdapter;
import com.paypal.psix.models.Event;
import com.paypal.psix.services.FacebookService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shay on 3/3/15.
 */
public class EventsFragment extends Fragment {

    private static final String LOG_TAG = "Events";

    @InjectView(R.id.events_list_view) ListView listView;

    EventsAdapter adapter;
    ArrayList<Event> data = new ArrayList<>();

    public EventsFragment() {
    }

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
        syncData();

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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

    private void syncData() {
        FacebookService.getUserCreatedFutureEvents(new Request.Callback() {
            public void onCompleted(Response response) {
                GraphObject mainObj = response.getGraphObject();
                if (mainObj != null) {
                    GraphObjectList<GraphObject> objects = mainObj.getPropertyAsList("data", GraphObject.class);
                    for (GraphObject obj : objects) {
                        createEventFromGraphObject(obj);
                    }
                    refreshDataSource();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private Event createEventFromGraphObject(GraphObject obj) {
        Event event = new Event();
        event.fbEventId = (String)obj.getProperty("id");
        event.name = (String)obj.getProperty("name");
        event.imageURL = (String)obj.getPropertyAs("cover", GraphObject.class).getProperty("source");

        SimpleDateFormat format;
        String dateString = (String) obj.getProperty("start_time");
        Date date;
        try {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            date = format.parse(dateString);
            event.timestamp = date.getTime();
        } catch (ParseException e1) {
            try {
                format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(dateString);
                event.timestamp = date.getTime();
            } catch (ParseException e2) {

            }
        }
        event.save();
        return event;
    }
}