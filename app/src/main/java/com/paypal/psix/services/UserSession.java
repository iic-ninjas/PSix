package com.paypal.psix.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.paypal.psix.PSixApplication;
import com.paypal.psix.models.User;

import bolts.Task;

public class UserSession {

    private static final String LOG_TAG = UserSession.class.getSimpleName();

    private static final String CUR_USER_FBID_KEY = "cur_user_fbid";
    private static final String CUR_USER_FIRST_NAME_KEY = "cur_user_first_name";
    private static final String CUR_USER_LAST_NAME_KEY = "cur_user_last_name";

    private static SharedPreferences getSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(PSixApplication.getAppContext());
    }

    private UserSession(User currentUser) {
        SharedPreferences.Editor editor = getSharedPrefs().edit();

        editor.putString(CUR_USER_FBID_KEY, currentUser.fbUserId);
        editor.putString(CUR_USER_FIRST_NAME_KEY, currentUser.firstName);
        editor.putString(CUR_USER_LAST_NAME_KEY, currentUser.lastName);
        editor.apply();

        sessionInstance = this;
    }

    public User getUser() {
        String fbId = getSharedPrefs().getString(CUR_USER_FBID_KEY, null);
        if (fbId != null) {
            return new User(
                    fbId,
                    getSharedPrefs().getString(CUR_USER_FIRST_NAME_KEY, null),
                    getSharedPrefs().getString(CUR_USER_LAST_NAME_KEY, null)
            );
        }

        return null;
    }

    public static void setUser(GraphUser newUser) {
        new UserSession(new User(newUser.getId(), newUser.getFirstName(), newUser.getLastName()));
    }

    private static UserSession sessionInstance;

    public static class UserSessionException extends Exception {

        private FacebookRequestError fbReqError;

        public UserSessionException(FacebookRequestError fbReqError) {
            super(fbReqError.getErrorMessage());
            this.fbReqError = fbReqError;
        }

        public FacebookRequestError getFbReqError() {
            return fbReqError;
        }

    }

}