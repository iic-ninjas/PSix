package com.paypal.psix.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.paypal.psix.R;

public class EventsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        } else if (id == R.id.action_debug_setup_event) {
//            startActivity(new Intent(this, SetupEventActivity.class));
//            return true;
//        } else if (id == R.id.action_debug_events_list) {
//            startActivity(new Intent(this, EventsActivity.class));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
