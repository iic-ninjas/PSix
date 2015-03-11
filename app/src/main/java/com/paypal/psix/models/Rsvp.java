package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Rsvps")
public class Rsvp extends Model {

    @Column(name = "Amount")
    public int amount;

    // 'not_replied', 'unsure', 'attending', or 'declined'
    @Column(name = "RsvpStatus")
    public String status;

    @Column(name = "Event", index = true)
    public Event event;

    @Column(name = "User", index = true)
    public User user;

    public Rsvp() {
        super();
    }

    public Rsvp(Event event, User user) {
        this(-1, "not_replied", event, user);
    }

    public Rsvp(int amount, String status, Event event, User user) {
        super();
        this.amount = amount;
        this.status = status;
        this.event = event;
        this.user = user;
    }
}
