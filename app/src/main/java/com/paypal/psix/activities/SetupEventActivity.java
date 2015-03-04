package com.paypal.psix.activities;

import android.os.Bundle;
import android.view.Menu;

import com.paypal.psix.R;
import com.paypal.psix.models.Event;

import org.parceler.Parcels;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventActivity extends ActivityWithSettings {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = Parcels.unwrap(getIntent().getExtras().getParcelable(Event.TAG));
        setContentView(R.layout.activity_setup_event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
