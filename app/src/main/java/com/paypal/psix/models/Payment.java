package com.paypal.psix.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Payments")
public class Payment extends Model {

    @Column(name = "Id", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    public String id;

    @Column(name = "Amount")
    public int amount;

    public Payment() {
        super();
    }

    public Payment(String id, int amount) {
        super();
        this.id = id;
        this.amount = amount;
    }
}
