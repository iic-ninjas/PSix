package com.paypal.psix.services;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by shay on 3/11/15.
 */
public interface ParseInterface {

    static String APP_ID  = "3VYFRciY4tMgYwEwVYq2veNGbqq7CjJvQUp85IAo";
    static String API_KEY = "LTNEwupT7LOtVvWoVVVkFahN2H76oDU0qbEJSL3b";

    // EVENT FETCHING



    // EVENT CREATION

    @Headers({
        "X-Parse-Application-Id: " + APP_ID,
        "X-Parse-REST-API-Key: " + API_KEY,
        "Content-Type: application/json"
    })
    @POST("/1/classes/Event")
    void createEvent(@Body ParseEventCreateParams params,
                     Callback<ParseEvent> cb);

    public static class ParseEventCreateParams {
        public String fbId;
        public String fbUserId;
        public String description;
        public int amountPer;

        public ParseEventCreateParams(String fbId, String fbUserId, String description, int amountPer) {
            this.fbId = fbId;
            this.fbUserId = fbUserId;
            this.description = description;
            this.amountPer = amountPer;
        }

    }

    public class ParseEvent {
    }
}