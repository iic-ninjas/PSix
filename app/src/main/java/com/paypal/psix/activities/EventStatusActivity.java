package com.paypal.psix.activities;

import android.os.Bundle;

import com.paypal.psix.R;
import com.paypal.psix.models.Event;

public class EventStatusActivity extends PSixActionBarActivity {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = Event.find(getIntent().getExtras().getLong(Event.TAG));
        setContentView(R.layout.activity_event_status);
    }
}
