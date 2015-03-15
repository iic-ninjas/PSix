package com.paypal.psix.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.paypal.psix.R;
import com.paypal.psix.fragments.ShareDialogFragment;
import com.paypal.psix.models.Event;
import com.paypal.psix.utils.HasEvent;

public class EventStatusActivity extends PSixActionBarActivity implements HasEvent {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = Event.find(getIntent().getExtras().getLong(Event.TAG));
        setContentView(R.layout.activity_event_status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            ShareDialogFragment.show(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Event getEvent() {
        return event;
    }
}
