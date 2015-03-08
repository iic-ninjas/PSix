package com.paypal.psix.activities;

import android.os.Bundle;

import com.paypal.psix.R;

public class EventsActivity extends PSixActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setupActionBar();
    }

    private void setupActionBar() {
        setTitle(" " + getString(R.string.your_events));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }
}
