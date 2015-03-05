package com.paypal.psix.utils;

import java.util.regex.Pattern;

/**
 * Created by shay on 3/4/15.
 */
public class EmailValidator {

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    );

    public static boolean validate(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
