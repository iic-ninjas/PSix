package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {

    @Column(name = "FbUserId", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String fbUserId;

    @Column(name = "Name")
    public String name;

    public User() {
        super();
    }

    public User(String fbUserId, String name) {
        super();
        this.fbUserId = fbUserId;
        this.name = name;
    }
}
