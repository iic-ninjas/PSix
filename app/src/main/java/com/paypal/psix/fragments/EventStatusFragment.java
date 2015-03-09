package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.psix.R;
import com.paypal.psix.activities.EventStatusActivity;
import com.paypal.psix.models.Event;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shay on 3/8/15.
 */
public class EventStatusFragment extends Fragment {

    @InjectView(R.id.event_name_text_view) TextView eventTitle;
    @InjectView(R.id.event_header_image) ImageView eventImageHeader;
    @InjectView(R.id.event_header_date) TextView eventDateHeader;

    Event event;

    public EventStatusFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_status, container, false);
        ButterKnife.inject(this, rootView);

        event = ((EventStatusActivity)getActivity()).event;
        eventTitle.setText(event.name);
        eventDateHeader.setText(event.getFormattedDate());
        Picasso.with(getActivity()).load(event.imageURL).into(eventImageHeader);
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
