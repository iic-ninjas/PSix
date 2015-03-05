package com.paypal.psix.adapters;

import android.content.Context;
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

    static class ViewHolder {
        @InjectView(R.id.event_item_image) ImageView imageView;
        @InjectView(R.id.event_item_title) TextView titleLabel;
        @InjectView(R.id.event_item_date) TextView dateLabel;
        @InjectView(R.id.event_item_desc) TextView descLabel;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
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
        holder.dateLabel.setText(event.getFormattedDate());
        Picasso.with(getContext()).load(event.imageURL).into(holder.imageView);

        return view;
    }
}
