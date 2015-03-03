package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Rsvps")
public class Rsvp extends Model {

    @Column(name = "Id", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String id;

    @Column(name = "Amount")
    public int amount;

    @Column(name = "Event")
    public Event event;

    @Column(name = "User")
    public User user;

    public Rsvp() {
        super();
    }

    public Rsvp(String id, int amount, Event event, User user) {
        super();
        this.id = id;
        this.amount = amount;
        this.event = event;
        this.user = user;
    }
}
