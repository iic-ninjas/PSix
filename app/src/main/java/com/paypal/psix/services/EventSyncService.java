package com.paypal.psix.services;

import com.paypal.psix.models.Event;
import com.paypal.psix.models.User;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by shay on 3/11/15.
 */
public class EventSyncService {

    public static void getEventsOfUser(User user) {

    }

    public static void pluginEvent(final Event event, Callback<ParseInterface.ParseEvent> cb) {
        client().createEvent(new ParseInterface.ParseEventCreateParams(event.fbEventId, UserSession.getUser().fbUserId, event.paymentDescription, event.amountPerUser), cb);
    }

    private static ParseInterface client() {
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint("https://api.parse.com")
            .build();

        return restAdapter.create(ParseInterface.class);
    }
}
