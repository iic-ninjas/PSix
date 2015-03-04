package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.parceler.Parcel;

import java.util.List;

@Parcel(value = Parcel.Serialization.BEAN, analysisLimit = Model.class)
@Table(name = "Users")
public class User extends Model {

    @Column(name = "FbUserId", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String fbUserId;

    @Column(name = "Name")
    public String name;

    public List<Event> events() {
        return getMany(Event.class, "Organizer");
    }

    public List<Rsvp> rsvps() {
        return getMany(Rsvp.class, "User");
    }

    public User() {
        super();
    }

    public User(String fbUserId, String name) {
        super();
        this.fbUserId = fbUserId;
        this.name = name;
    }
}
