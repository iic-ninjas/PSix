package com.paypal.psix.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.paypal.psix.R;

import org.fluttercode.datafactory.impl.DataFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Table(name = "Events")
public class Event extends Model {

    public static final String TAG = "Events";
    public static final String SHARE_URL_BASE = "psix.parseapp.com?e=";

    @Column(name = "FbEventId", unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    public String fbEventId;

    @Column(name = "Organizer", index = true, onDelete = Column.ForeignKeyAction.CASCADE)
    public User organizer;

    @Column(name = "Name")
    public String name;

    @Column(name = "ShareURL")
    public String shareURL;

    @Column(name = "AmountPerUser")
    public int amountPerUser;

    @Column(name = "PaymentDescription")
    public String paymentDescription;

    @Column(name = "ImageURL")
    public String imageURL;

    @Column(name = "HasSetup")
    public boolean hasSetup;

    @Column(name = "Timestamp", index = true)
    public long timestamp;

    public List<Rsvp> rsvps() {
        return getMany(Rsvp.class, "Event");
    }

    public Event() {
        super();
    }

    public static Event GenerateRandomEvent() {
        DataFactory df = new DataFactory();
        Event event = new Event();
        event.name = df.getRandomWord() + " " + df.getRandomWord() + " " + df.getRandomWord();
        event.imageURL = "http://lorempixel.com/200/" + (200 + df.getNumberBetween(0, 100)) + "/";
        event.timestamp = df.getDate(new Date(), 0, 20).getTime();
        event.save();
        return event;
    }

    public Event(String fbEventId, User organizer, String name, String shareURL, String imageURL, int amountPerUser, String paymentDescription, int timestamp, boolean hasSetup) {
        super();
        this.fbEventId = fbEventId;
        this.organizer = organizer;
        this.name = name;
        this.amountPerUser = amountPerUser;
        this.paymentDescription = paymentDescription;
        this.shareURL = shareURL;
        this.imageURL = imageURL;
        this.timestamp = timestamp;
        this.hasSetup = hasSetup;
    }

    public Event setup() {
        this.hasSetup = true;
        this.save();
        return this;
    }

    public Event desetup() {
        this.hasSetup = false;
        this.save();
        return this;
    }

    public String getShareURL() { return SHARE_URL_BASE + this.fbEventId; }

    public String getShareMessage(Context context) {
        return String.format(context.getString(R.string.share_message), paymentDescription, amountPerUser, getShareURL());
    }

    public int getNumberOfInvitees() { return this.rsvps().size() - 1; }

    public Date getDate() {
        return new Date(timestamp);
    }

    public String getShortFormattedDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM");
        return fmt.format(getDate());
    }

    public String getFormattedDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM HH:mm");
        return fmt.format(getDate());
    }

    public static Event find(long id) {
        return new Select().from(Event.class).where("id = ?", id).executeSingle();
    }
}
