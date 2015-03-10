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

    private static final String COMPLEX_FB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String SIMPLE_FB_DATE_FORMAT  = "yyyy-MM-dd";

    public interface EventsSyncCallback {
        void eventsSyncedCallback();
    }

    public static void syncFacebookEvent(final EventsSyncCallback callbackHandler) {
        FacebookService.getUserCreatedFutureEvents(new Request.Callback() {
            public void onCompleted(Response response) {
                GraphObject mainObj = response.getGraphObject();
                if (mainObj != null) {
                    GraphObjectList<GraphObject> objects = mainObj.getPropertyAsList("data", GraphObject.class);
                    for (GraphObject obj : objects) createEventFromGraphObject(obj);
                    callbackHandler.eventsSyncedCallback();
                }
            }
        });
    }

    private static Event createEventFromGraphObject(GraphObject obj) {
        Event event = new Event();
        event.fbEventId = (String)obj.getProperty("id");
        event.name      = (String)obj.getProperty("name");
        event.imageURL  = (String)obj.getPropertyAs("cover", GraphObject.class).getProperty("source");
        event.timestamp = parseTimestampFromGraphObject(obj);
        event.save();
        return event;
    }

    private static long parseTimestampFromGraphObject(GraphObject obj) {
        SimpleDateFormat format;
        String dateString = (String) obj.getProperty("start_time");
        Date date;
        try {
            format = new SimpleDateFormat(COMPLEX_FB_DATE_FORMAT);
            date = format.parse(dateString);
            return date.getTime();
        } catch (ParseException e1) {
            try {
                format = new SimpleDateFormat(SIMPLE_FB_DATE_FORMAT);
                date = format.parse(dateString);
                return date.getTime();
            } catch (ParseException e2) {
                return 0;
            }
        }
    }
}
