package com.paypal.psix.utils;

import android.test.AndroidTestCase;

/**
 * Created by shay on 3/4/15.
 */
public class EmailValidatorTests extends AndroidTestCase {

    public void testEmailValidation() throws Throwable {
        assertTrue(EmailValidator.validate("email@gmail.com"));
        assertTrue(EmailValidator.validate("e.mail@gmail.com"));
        assertTrue(EmailValidator.validate("e.ma.il@gmail.com"));
        assertTrue(EmailValidator.validate("e+mai+l@gmail.com"));
        assertTrue(EmailValidator.validate("email@gmail.co.il"));

        assertFalse(EmailValidator.validate(""));
        assertFalse(EmailValidator.validate("email@"));
        assertFalse(EmailValidator.validate("email@gmail"));
        assertFalse(EmailValidator.validate("email"));
    }
}
