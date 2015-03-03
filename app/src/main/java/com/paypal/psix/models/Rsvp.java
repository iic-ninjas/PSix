package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Rsvps")
public class Rsvp extends Model {

    public enum RsvpStatus {
        NOT_ATTENDING,
        MAYBE,
        ATTENDING
    }

    @Column(name = "Id", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String id;

    @Column(name = "Amount")
    public int amount;

    @Column(name = "Status")
    public RsvpStatus status;

    @Column(name = "Event")
    public Event event;

    @Column(name = "User")
    public User user;

    public Rsvp() {
        super();
    }

    public Rsvp(String id, int amount, RsvpStatus status, Event event, User user) {
        super();
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.event = event;
        this.user = user;
    }
}
