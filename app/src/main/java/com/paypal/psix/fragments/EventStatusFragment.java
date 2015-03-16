package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.paypal.psix.R;
import com.paypal.psix.activities.EventStatusActivity;
import com.paypal.psix.adapters.RsvpsAdapter;
import com.paypal.psix.models.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EventStatusFragment extends Fragment {

    @InjectView(R.id.event_name_text_view) TextView eventTitle;
    @InjectView(R.id.event_header_image) ImageView eventImageHeader;
    @InjectView(R.id.event_header_date) TextView eventDateHeader;
    @InjectView(R.id.rsvp_list_view) ListView rsvpListView;

    Event event;
    RsvpsAdapter rsvpsAdapter;

    public EventStatusFragment() {}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_status, container, false);
        ButterKnife.inject(this, rootView);

        event = ((EventStatusActivity)getActivity()).event;
        eventTitle.setText(event.name);
        eventDateHeader.setText(event.getFormattedDate());
        Picasso.with(getActivity()).load(event.imageURL).into(eventImageHeader);
        rsvpsAdapter = new RsvpsAdapter(getActivity(), new ArrayList<>(event.rsvps()));
        rsvpListView.setAdapter(rsvpsAdapter);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
