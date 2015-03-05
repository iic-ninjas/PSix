package com.paypal.psix.activities;

import android.os.Bundle;

import com.paypal.psix.R;
import com.paypal.psix.models.Event;

import org.parceler.Parcels;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventActivity extends PSixActionBarActivity {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = Parcels.unwrap(getIntent().getExtras().getParcelable(Event.TAG));
        setContentView(R.layout.activity_setup_event);
    }
}
