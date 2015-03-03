package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Events")
public class Event extends Model {

    @Column(name = "FbEventId", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String fbEventId;

    @Column(name = "Name")
    public String name;

    @Column(name = "ShareURL")
    public String shareURL;

    @Column(name = "AmountPerUser")
    public int amountPerUser;

    @Column(name = "Timestamp")
    public int timestamp;

    public Event() {
        super();
    }

    public Event(String fbEventId, String name, String shareURL, int amountPerUser, int timestamp) {
        super();
        this.fbEventId = fbEventId;
        this.name = name;
        this.amountPerUser = amountPerUser;
        this.shareURL = shareURL;
        this.timestamp = timestamp;
    }
}
