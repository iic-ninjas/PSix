package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.fluttercode.datafactory.impl.DataFactory;
import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Parcel(value = Parcel.Serialization.BEAN, analysisLimit = Model.class)
@Table(name = "Events")
public class Event extends Model {

    public static final String TAG = "Events";

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

    @Column(name = "PaymentDescription")
    public String paymentDescription;

    @Column(name = "Timestamp", index = true)
    public long timestamp;

    public List<Rsvp> rsvps() {
        return getMany(Rsvp.class, "Event");
    }

    public Event() {
        super();
    }

    public Event(String name) {
        this(null, null, name, null, 0, null, 0);
    }

    public static Event GenerateRandomEvent() {
        DataFactory df = new DataFactory();
        Event event = new Event();
        event.name = df.getRandomWord() + " " + df.getRandomWord() + " " + df.getRandomWord();
        event.timestamp = df.getDate(new Date(), 0, 20).getTime();
        return event;
    }

    public Event(String fbEventId, User organizer, String name, String shareURL, int amountPerUser, String paymentDescription, int timestamp) {
        super();
        this.fbEventId = fbEventId;
        this.organizer = organizer;
        this.name = name;
        this.amountPerUser = amountPerUser;
        this.paymentDescription = paymentDescription;
        this.shareURL = shareURL;
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return new Date(timestamp);
    }

    public String getFormattedDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/mm/yy");
        return fmt.format(getDate());
    }
}
