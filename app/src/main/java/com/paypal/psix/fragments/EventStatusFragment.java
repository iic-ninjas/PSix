package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.paypal.psix.R;
import com.paypal.psix.activities.EventStatusActivity;
import com.paypal.psix.adapters.RsvpsAdapter;
import com.paypal.psix.models.Event;
import com.paypal.psix.models.Rsvp;
import com.paypal.psix.models.User;
import com.paypal.psix.services.ParseAPI;
import com.paypal.psix.services.ParseEventsSyncService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;

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
        Picasso.with(getActivity()).load(event.imageURL).resize(128,128).centerCrop().into(eventImageHeader);
        rsvpsAdapter = new RsvpsAdapter(getActivity(), new ArrayList<>(event.rsvps()));
        rsvpListView.setAdapter(rsvpsAdapter);
        syncPayments();

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void syncPayments(){
        // Fetch payments per event from Parse
        ParseEventsSyncService.getPaymentsPerEvent(event, new Callback<ParseAPI.ParsePaymentsResults>() {

            @Override
            public void success(ParseAPI.ParsePaymentsResults results, retrofit.client.Response response) {
                int sumAmount = 0;
                for (ParseAPI.ParsePayment parsePayment : results.results) {
                    sumAmount += parsePayment.amount;

                    if (parsePayment.userFbId == null) continue;
                    User user =  new Select().from(User.class).where("FbUserId = ?", parsePayment.userFbId).executeSingle();
                    if (user == null) continue;
                    Rsvp rsvp = new Select().from(Rsvp.class).where("User = ? AND Event = ?", user.getId(), event.getId()).executeSingle();
                    if (rsvp == null) rsvp = new Rsvp(event, user);
                    rsvp.amount = parsePayment.amount;
                    rsvp.save();
                }
                updateMoneyCollection(sumAmount);
            }

            @Override
            public void failure(RetrofitError error) {
                updateMoneyCollection(0);
            }
        });

    }

    private void updateMoneyCollection(int amountFromParse) {
        perAttendeeAmount.setText(String.format("$%d", event.amountPerUser));
        rsvpsAdapter.notifyDataSetChanged();

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

        moneyCollectedAmount.setText(String.format("$%d of $%d", Math.max(collected, amountFromParse), toCollect));
        paidStatus.setText(String.format("%d of %d attending", totalAttendeesPaid, attendees.size()));
    }

}
