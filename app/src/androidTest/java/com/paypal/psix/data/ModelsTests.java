package com.paypal.psix.data;

import com.paypal.psix.helpers.Factories;
import com.paypal.psix.models.User;
import com.paypal.psix.models.Event;

public class ModelsTests extends DBTest {

    public void testUserCreation() throws Throwable {
        String fbUserId = Factories.rndData.getNumberText(Factories.FB_ID_LENGTH);
        String name = Factories.rndData.getName();

        User user = Factories.createUser(fbUserId, name);

        assertEquals(fbUserId, user.fbUserId);
        assertEquals(name, user.name);
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

        assertEquals(fbEventId, event.fbEventId);
        assertEquals(name, event.name);
        assertEquals(user, event.organizer);
        assertEquals(shareURL, event.shareURL);
        assertEquals(amount, event.amountPerUser);
        assertEquals(timestamp, event.timestamp);
        assertEquals(description, event.paymentDescription);
    }
}
