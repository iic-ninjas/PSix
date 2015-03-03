package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Events")
public class Event extends Model {

    @Column(name = "FbEventId", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String fbEventId;

    @Column(name = "Organizer", index = true)
    public User organizer;

    @Column(name = "Name")
    public String name;

    @Column(name = "ShareURL")
    public String shareURL;

    @Column(name = "AmountPerUser")
    public int amountPerUser;

    @Column(name = "Timestamp", index = true)
    public int timestamp;

    public List<Rsvp> rsvps() {
        return getMany(Rsvp.class, "Event");
    }

    public Event() {
        super();
    }

    public Event(String name) {
        this(null, null, name, null, 0, 0);
    }

    public Event(String fbEventId, User organizer, String name, String shareURL, int amountPerUser, int timestamp) {
        super();
        this.fbEventId = fbEventId;
        this.organizer = organizer;
        this.name = name;
        this.amountPerUser = amountPerUser;
        this.shareURL = shareURL;
        this.timestamp = timestamp;
    }
}
