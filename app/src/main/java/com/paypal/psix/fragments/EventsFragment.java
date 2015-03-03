package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.paypal.psix.R;
import com.paypal.psix.adapters.EventsAdapter;
import com.paypal.psix.models.Event;

import java.util.ArrayList;
import java.util.Arrays;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.inject(this, rootView);

        // Demo data.
        Event[] array = {
            new Event("Event 1"),
            new Event("Event 2"),
            new Event("Event 3"),
            new Event("Event 4"),
            new Event("Event 5"),
            new Event("Event 6"),
            new Event("Event 7"),
            new Event("Event 8"),
            new Event("Event 9")
        };
        listView.setAdapter(new EventsAdapter(getActivity(), new ArrayList<>(Arrays.asList(array))));

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}