package com.paypal.psix.services;

import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

/**
 * Created by shay on 3/10/15.
 */
public class FacebookService {

    private static String FB_EVENT_FIELDS = "cover,name,id,start_time";

    public static void getUserCreatedFutureEvents(Request.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", FB_EVENT_FIELDS);

        new Request(
            Session.getActiveSession(),
            "/me/events",
            params,
            HttpMethod.GET,
            callback
        ).executeAsync();
    }
}
