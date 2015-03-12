package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Users")
public class User extends Model {

    @Column(name = "FbUserId", unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    public String fbUserId;

    @Column(name = "FirstName")
    public String firstName;

    @Column(name = "LastName")
    public String lastName;

    public List<Event> events() {
        return getMany(Event.class, "Organizer");
    }

    public List<Rsvp> rsvps() {
        return getMany(Rsvp.class, "User");
    }

    public User() {
        super();
    }

    public User(String fbUserId, String firstName, String lastName) {
        super();
        this.fbUserId = fbUserId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAvatarURL() {
        return "http://graph.facebook.com/" + this.fbUserId + "/picture?type=square";
    }

    public String getFullName() {
        String fullName = firstName;
        if (lastName != null || lastName != "") fullName += " " + lastName;
        return fullName;
    }
}
