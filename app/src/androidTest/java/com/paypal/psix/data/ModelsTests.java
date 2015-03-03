package com.paypal.psix.data;

import com.paypal.psix.helpers.Factories;
import com.paypal.psix.models.Event;
import com.paypal.psix.models.User;

public class ModelsTests extends DBTest {

    public void testUserCreation() throws Throwable {
        String fbUserId = Factories.rndData.getNumberText(Factories.FB_ID_LENGTH);
        String name = Factories.rndData.getName();

        User user = Factories.createUser(fbUserId, name);

        assertEquals(user.fbUserId, fbUserId);
        assertEquals(user.name, name);
    }

    public void testEventCreation() throws Throwable {
        Event event = new Event();

        String fbEventId = Factories.rndData.getNumberText(Factories.FB_ID_LENGTH);
        String name = Factories.rndData.getName();
        String shareURL = Factories.rndData.getName();
        int amount = Factories.rndData.getNumberBetween(0, 50);
        long timestamp = Factories.rndData.getBirthDate().getTime();
        String description = Factories.rndData.getName();
        User user = Factories.createRandomUser();

        event.fbEventId = fbEventId;
        event.name = name;
        event.organizer = user;
        event.shareURL = shareURL;
        event.amountPerUser = amount;
        event.timestamp = timestamp;
        event.paymentDescription = description;

        assertEquals(event.fbEventId, fbEventId);
        assertEquals(event.name, name);
        assertEquals(event.organizer, user);
        assertEquals(event.shareURL, shareURL);
        assertEquals(event.amountPerUser, amount);
        assertEquals(event.timestamp, timestamp);
        assertEquals(event.paymentDescription, description);
    }
}
