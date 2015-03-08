package com.paypal.psix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.paypal.psix.R;
import com.paypal.psix.activities.SetupEventActivity;
import com.paypal.psix.adapters.EventsAdapter;
import com.paypal.psix.models.Event;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shay on 3/3/15.
 */
public class EventsFragment extends Fragment {

    @InjectView(R.id.events_list_view) ListView listView;

    public EventsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.inject(this, rootView);


        listView.setAdapter(new EventsAdapter(getActivity(), eventsDataSource()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event event = (Event)adapterView.getItemAtPosition(position);
                if (event != null) {
                    Intent intent = new Intent(getActivity(), SetupEventActivity.class);
                    intent.putExtra(Event.TAG, Parcels.wrap(event));
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private ArrayList<Event> eventsDataSource() {
        // Demo data.
        Event[] array = {
                Event.GenerateRandomEvent(), Event.GenerateRandomEvent(), Event.GenerateRandomEvent(),
                Event.GenerateRandomEvent(), Event.GenerateRandomEvent(), Event.GenerateRandomEvent(),
                Event.GenerateRandomEvent(), Event.GenerateRandomEvent(), Event.GenerateRandomEvent(),
                Event.GenerateRandomEvent(), Event.GenerateRandomEvent(), Event.GenerateRandomEvent()
        };

        ArrayList<Event> arrayList = new ArrayList<>(Arrays.asList(array));

        Collections.sort(arrayList, new Comparator<Event>() {
            public int compare(Event e1, Event e2) {
                return e1.timestamp > e2.timestamp ? 1 : -1;
            }
        });

        return arrayList;

    }
}