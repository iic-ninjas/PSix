package com.paypal.psix.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.psix.R;
import com.paypal.psix.models.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shay on 3/3/15.
 */
public class EventsAdapter extends ArrayAdapter<Event> {

    private static final String LOG_TAG = EventsAdapter.class.getSimpleName();
    private static final int MAX_DESCRIPTION_LENGTH = 19;

    private static final int SETUPED_ICON_UNICODE = 0xf251;
    private static final int NOT_SETUPED_ICON_UNICODE = 0xf2f7;

    static class ViewHolder {
        public static int BASE_ROTATION = -12;
        public static int MAX_ROTATION = 10;

        @InjectView(R.id.event_item_image) ImageView imageView;
        @InjectView(R.id.event_item_title) TextView titleLabel;
        @InjectView(R.id.event_item_date) TextView dateLabel;
        @InjectView(R.id.event_item_action_icon) TextView actionIcon;
        @InjectView(R.id.event_item_desc) TextView descLabel;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            dateLabel.setRotation(BASE_ROTATION + (float)((int)(Math.random() * 2 * MAX_ROTATION) - MAX_ROTATION));
        }
    }

    public EventsAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Event event = getItem(position);

        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder)view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_event_fragment, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.titleLabel.setText(event.name);
        holder.dateLabel.setText(event.getShortFormattedDate());

        String eventDescription = event.description == null ? "" : event.description;
        if (eventDescription.length() > MAX_DESCRIPTION_LENGTH) {
            eventDescription = eventDescription.substring(0, MAX_DESCRIPTION_LENGTH - 3) + "...";
        }

        holder.descLabel.setText(eventDescription);
        Picasso.with(getContext()).load(event.imageURL).resize(128,128).centerCrop().into(holder.imageView);

        int iconUnicode = event.hasSetup ? SETUPED_ICON_UNICODE : NOT_SETUPED_ICON_UNICODE;
        holder.actionIcon.setText(Character.toString((char)iconUnicode));

        return view;
    }
}
