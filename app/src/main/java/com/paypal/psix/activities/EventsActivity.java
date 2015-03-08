package com.paypal.psix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_debug_start_onboarding) {
            startActivity(new Intent(this, OnboardingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
