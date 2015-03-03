package com.paypal.psix.data;

import android.test.AndroidTestCase;

/**
 * Used as a basic test suit for tests involving the DB.
 */

public class DBTest extends AndroidTestCase {

    final static String DB_NAME = "PSix.db";

    void resetDb() {
        mContext.deleteDatabase(DB_NAME);
    }

    public void setUp() {
        resetDb();
    }
}