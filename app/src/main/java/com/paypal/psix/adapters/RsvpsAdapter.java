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
import com.paypal.psix.models.Rsvp;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RsvpsAdapter extends ArrayAdapter<Rsvp> {

    private static final String LOG_TAG = RsvpsAdapter.class.getSimpleName();

    static class ViewHolder {
        @InjectView(R.id.rsvp_user_profile_image) ImageView profileImageView;
        @InjectView(R.id.rsvp_user_name) TextView userNameLabel;
        @InjectView(R.id.rsvp_status) TextView rsvpStatusLabel;
        @InjectView(R.id.rsvp_payment_sum) TextView paymentSumLabel;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public RsvpsAdapter(Context context, ArrayList<Rsvp> rsvps) {
        super(context, 0, rsvps);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Rsvp rsvp = getItem(position);

        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder)view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.rsvp_item_fragment, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.userNameLabel.setText(rsvp.user.getFullName());
        holder.rsvpStatusLabel.setText(rsvp.getFormattedStatus());
        holder.paymentSumLabel.setText(rsvp.hasPaid() ? rsvp.getFormattedAmount() : "");
        Log.d(LOG_TAG, "user avatar url: " + rsvp.user.getAvatarURL());
        Picasso.with(getContext()).load(rsvp.user.getAvatarURL()).into(holder.profileImageView);

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
