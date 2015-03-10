package com.paypal.psix.services;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.paypal.psix.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shay on 3/10/15.
 */

public class FacebookSyncService {

    public interface EventsSyncCallback {
        void eventsSyncedCallback();
    }

    public static void syncFacebookEvent(final EventsSyncCallback callbackHandler) {
        FacebookService.getUserCreatedFutureEvents(new Request.Callback() {
            public void onCompleted(Response response) {
                GraphObject mainObj = response.getGraphObject();
                if (mainObj != null) {
                    GraphObjectList<GraphObject> objects = mainObj.getPropertyAsList("data", GraphObject.class);
                    for (GraphObject obj : objects) {
                        createEventFromGraphObject(obj);
                    }
                    callbackHandler.eventsSyncedCallback();
                }
            }
        });
    }

    private static Event createEventFromGraphObject(GraphObject obj) {
        Event event = new Event();
        event.fbEventId = (String)obj.getProperty("id");
        event.name = (String)obj.getProperty("name");
        event.imageURL = (String)obj.getPropertyAs("cover", GraphObject.class).getProperty("source");

        SimpleDateFormat format;
        String dateString = (String) obj.getProperty("start_time");
        Date date;
        try {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            date = format.parse(dateString);
            event.timestamp = date.getTime();
        } catch (ParseException e1) {
            try {
                format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(dateString);
                event.timestamp = date.getTime();
            } catch (ParseException e2) {

            }
        }
        event.save();
        return event;
    }
}
