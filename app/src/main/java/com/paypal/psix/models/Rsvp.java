package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.parceler.Parcel;

@Parcel(value = Parcel.Serialization.BEAN, analysisLimit = Model.class)
@Table(name = "Rsvps")
public class Rsvp extends Model {

    public enum RsvpStatus {
        NOT_ATTENDING,
        MAYBE,
        ATTENDING
    }

    @Column(name = "RemoteId", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String remoteId;

    @Column(name = "Amount", index = true)
    public int amount;

    @Column(name = "Status", index = true)
    public RsvpStatus status;

    @Column(name = "Event", index = true)
    public Event event;

    @Column(name = "User", index = true)
    public User user;

    public Rsvp() {
        super();
    }

    public Rsvp(String remoteId, int amount, RsvpStatus status, Event event, User user) {
        super();
        this.remoteId = remoteId;
        this.amount = amount;
        this.status = status;
        this.event = event;
        this.user = user;
    }
}
