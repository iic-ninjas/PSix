package com.paypal.psix.services;

import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.paypal.psix.models.Event;
import com.paypal.psix.models.Rsvp;
import com.paypal.psix.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                    ArrayList<Event> events = new ArrayList<>();
                    GraphObjectList<GraphObject> objects = mainObj.getPropertyAsList("data", GraphObject.class);
                    for (GraphObject obj : objects) {
                        Event event = createEventFromGraphObject(obj);
                        events.add(event);
                    }
                    syncInviteesForEvents(events, callbackHandler);
                }
            }
        });
    }

    private static void syncInviteesForEvents(ArrayList<Event> events, final EventsSyncCallback callbackHandler) {
        ArrayList<Request> requests = new ArrayList<>();
        for (final Event event : events) {
            requests.add(FacebookService.getInviteesForEventRequest(event.fbEventId, new Request.Callback() {
                public void onCompleted(Response response) {
                    GraphObject mainObj = response.getGraphObject();
                    if (mainObj != null) {
                        ArrayList<Event> events = new ArrayList<>();
                        GraphObjectList<GraphObject> objects = mainObj.getPropertyAsList("data", GraphObject.class);
                        for (GraphObject obj : objects) {
                            createRsvpFromGraphObject(obj, event);
                        }
                    }
                }
            }));
        }

        RequestBatch requestBatch = new RequestBatch(requests);
        requestBatch.addCallback(new com.facebook.RequestBatch.Callback() {
            @Override
            public void onBatchCompleted(RequestBatch batch) {
                callbackHandler.eventsSyncedCallback();
            }
        });
        requestBatch.executeAsync();
    }

    private static Rsvp createRsvpFromGraphObject(GraphObject obj, Event event) {
        String[] names = ((String)obj.getProperty("name")).split(" ");

        User user = new User();
        user.fbUserId = (String)obj.getProperty("id");
        user.firstName = names[0];
        user.lastName = names[0];
        user.avatarURL = "http://graph.facebook.com/" + user.fbUserId + "/picture?type=square";
        user.save();

        Rsvp rsvp = new Rsvp();
        rsvp.event = event;
        rsvp.user = user;
        rsvp.save();
        return rsvp;
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
