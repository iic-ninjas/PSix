package com.paypal.psix.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.paypal.psix.R;
import com.paypal.psix.activities.EventStatusActivity;
import com.paypal.psix.adapters.RsvpsAdapter;
import com.paypal.psix.models.Event;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RsvpListFragment extends Fragment {

    private static final String LOG_TAG = RsvpListFragment.class.getSimpleName();

    @InjectView(R.id.rsvp_list_view) ListView rsvpListView;

    Event event;
    RsvpsAdapter adapter;
    ProgressDialog dialog;

    public RsvpListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rsvp_list, container, false);
        ButterKnife.inject(this, rootView);
        event = ((EventStatusActivity)getActivity()).event;

        adapter = new RsvpsAdapter(getActivity(), new ArrayList<>(event.rsvps()));
        rsvpListView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}