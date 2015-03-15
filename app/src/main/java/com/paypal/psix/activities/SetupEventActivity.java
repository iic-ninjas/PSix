package com.paypal.psix.activities;

import android.os.Bundle;

import com.paypal.psix.R;
import com.paypal.psix.models.Event;
import com.paypal.psix.utils.HasEvent;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventActivity extends PSixActionBarActivity implements HasEvent {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = Event.find(getIntent().getExtras().getLong(Event.TAG));
        setContentView(R.layout.activity_setup_event);
    }

    @Override
    public Event getEvent() {
        return event;
    }
}
