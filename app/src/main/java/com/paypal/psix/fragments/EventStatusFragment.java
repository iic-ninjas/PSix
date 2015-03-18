package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.paypal.psix.models.Rsvp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EventStatusFragment extends Fragment {

    private static final String LOG_TAG = EventStatusFragment.class.getSimpleName();

    @InjectView(R.id.event_name_text_view) TextView eventTitle;
    @InjectView(R.id.event_header_image) ImageView eventImageHeader;
    @InjectView(R.id.event_header_date) TextView eventDateHeader;
    @InjectView(R.id.rsvp_list_view) ListView rsvpListView;
    @InjectView(R.id.money_collected_amount) TextView moneyCollectedAmount;
    @InjectView(R.id.per_attendee_amount) TextView perAttendeeAmount;
    @InjectView(R.id.paid_status) TextView paidStatus;

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
        Picasso.with(getActivity()).load(event.imageURL).resize(64,64).centerCrop().into(eventImageHeader);
        rsvpsAdapter = new RsvpsAdapter(getActivity(), new ArrayList<>(event.rsvps()));
        rsvpListView.setAdapter(rsvpsAdapter);
        updateMoneyCollection();

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void updateMoneyCollection() {
        perAttendeeAmount.setText(String.format("$%d", event.amountPerUser));

        List<Rsvp> attendees = event.getAttendingGuests();
        int collected = 0;
        int toCollect = attendees.size() * event.amountPerUser;
        int totalAttendeesPaid = 0;
        for (Rsvp attendee : attendees) {
            if (attendee.hasPaid()) {
                collected += attendee.amount;
                totalAttendeesPaid++;
            }
        }

        moneyCollectedAmount.setText(String.format("$%d of $%d", collected, toCollect));
        paidStatus.setText(String.format("%d of %d attending", totalAttendeesPaid, attendees.size()));
    }

}
