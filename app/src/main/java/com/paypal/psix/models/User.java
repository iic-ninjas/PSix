package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.List;

@Table(name = "Users")
public class User extends Model {

    @Column(name = "FbUserId", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String fbUserId;

    @Column(name = "FirstName")
    public String firstName;

    @Column(name = "LastName")
    public String lastName;

    @Column(name = "AvatarURL")
    public String avatarURL;

    public List<Event> events() {
        return getMany(Event.class, "Organizer");
    }

    public List<Rsvp> rsvps() {
        return getMany(Rsvp.class, "User");
    }

    public User() {
        super();
    }

    public User(String fbUserId, String firstName, String lastName, String avatarURL) {
        super();
        this.fbUserId = fbUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarURL = avatarURL;
    }

    public static User GenerateRandomUser() {
        DataFactory df = new DataFactory();
        User user = new User();
        user.firstName = df.getFirstName();
        user.lastName = df.getLastName();
        user.avatarURL = "http://lorempixel.com/200/" + (200 + df.getNumberBetween(0, 100)) + "/";
        user.save();
        return user;
    }
}
