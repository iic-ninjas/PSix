package com.paypal.psix.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.paypal.psix.R;
import com.paypal.psix.fragments.EventsFragment;

public class EventsActivity extends PSixActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setupActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_refresh) {
            EventsFragment events = (EventsFragment) getSupportFragmentManager().findFragmentById(R.id.event);
            events.sync(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
