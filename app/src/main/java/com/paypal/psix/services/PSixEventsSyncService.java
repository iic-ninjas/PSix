package com.paypal.psix.services;

import com.activeandroid.query.Select;
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

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by shay on 3/10/15.
 */

public class PSixEventsSyncService {

    private static final String LOG_TAG = PSixEventsSyncService.class.getSimpleName();

    private static final String COMPLEX_FB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String SIMPLE_FB_DATE_FORMAT  = "yyyy-MM-dd";
    private static final String DEFAULT_EVENT_IMG = "http://wagner.wpengine.netdna-cdn.com/newsroom/wp-content/plugins/events-calendar-pro/resources/images/tribe-related-events-placeholder.png";

    public interface EventsSyncCallback {
        void eventsSyncedCallback();
    }

    public static void syncFacebookEvent(final EventsSyncCallback callbackHandler) {
        FacebookService.getUserCreatedFutureEvents(new Request.Callback() {
            public void onCompleted(Response response) {
                GraphObject mainObj = response.getGraphObject();
                if (mainObj != null) {
                    final ArrayList<Event> events = new ArrayList<>();
                    GraphObjectList<GraphObject> objects = mainObj.getPropertyAsList("data", GraphObject.class);
                    for (GraphObject obj : objects) {
                        Event event = createEventFromGraphObject(obj);
                        events.add(event);
                    }

                    syncEventsStatus(new EventsSyncCallback() {
                        @Override
                        public void eventsSyncedCallback() {
                            syncInviteesForEvents(events, callbackHandler);
                        }
                    });

                }
            }
        });
    }

    private static void syncEventsStatus(final EventsSyncCallback callbackHandler) {
        ParseEventsSyncService.getEventsOfUser(UserSession.getUser(), new Callback<ParseAPI.ParseResults>() {

            @Override
            public void success(ParseAPI.ParseResults results, retrofit.client.Response response) {
                for (ParseAPI.ParseEvent parseEvent : results.results) {
                    Event event = new Select().from(Event.class).where("FbEventId = ?", parseEvent.fbId).executeSingle();
                    if (event != null && !event.hasSetup) event.setup();
                }
                callbackHandler.eventsSyncedCallback();
            }

            @Override
            public void failure(RetrofitError error) {
                callbackHandler.eventsSyncedCallback();
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
        String fbId = (String) obj.getProperty("id");

        User user =  new Select().from(User.class).where("FbUserId = ?", fbId).executeSingle();;
        if (user == null) {
            String[] names = ((String)obj.getProperty("name")).split(" ");
            String firstName = names[0];
            String lastName = names.length == 2 ? names[1] : "";
            user = new User(fbId, firstName, lastName);
            user.save();
        }

        Rsvp rsvp = new Select().from(Rsvp.class).where("User = ? AND Event = ?", user.getId(), event.getId()).executeSingle();
        if (rsvp == null) rsvp = new Rsvp(event, user);
        rsvp.status = (String)obj.getProperty("rsvp_status");
        rsvp.save();
        return rsvp;
    }

    private static Event createEventFromGraphObject(GraphObject obj) {
        String fbEventId = (String) obj.getProperty("id");
        Event event = new Select().from(Event.class).where("FbEventId = ?", fbEventId).executeSingle();
        if (event == null) {
            event = new Event();
            event.fbEventId = fbEventId;
        }
        GraphObject coverURL = obj.getPropertyAs("cover", GraphObject.class);
        event.imageURL = coverURL != null ? (String)coverURL.getProperty("source") : DEFAULT_EVENT_IMG;
        event.name = (String) obj.getProperty("name");
        event.timestamp = parseTimestampFromGraphObject(obj);
        event.organizer = UserSession.getUser();
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
