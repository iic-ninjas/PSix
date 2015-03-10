package com.paypal.psix.services;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

/**
 * Created by shay on 3/10/15.
 */
public class FacebookService {

    public static void getUserCreatedFutureEvents(Request.Callback callback) {
        new Request(
            Session.getActiveSession(),
            "/me/events",
            null,
            HttpMethod.GET,
            callback
        ).executeAsync();
    }
}
