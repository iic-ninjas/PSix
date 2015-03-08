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
        return new User(
                getSharedPrefs().getString(CUR_USER_FBID_KEY, null),
                getSharedPrefs().getString(CUR_USER_FIRST_NAME_KEY, null),
                getSharedPrefs().getString(CUR_USER_LAST_NAME_KEY, null)
        );
    }


    private static UserSession sessionInstance;

    public static Task<User> connectWithFacebook(Session session) {
        Log.i(LOG_TAG, "Starting connection with facebook");
        final Task<User>.TaskCompletionSource taskCompletionSource = Task.create();

        Request.newMeRequest(session, new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser graphUser, Response response) {
                Log.i(LOG_TAG, "Connection to facebook completed");
                Log.i(LOG_TAG, "User is: " + graphUser.getFirstName());
                if (response.getError() != null) {
                    taskCompletionSource.setError(new UserSessionException(response.getError()));
                    return;
                }

                User user = new User(graphUser.getId(), graphUser.getFirstName(), graphUser.getLastName());
                new UserSession(user);

                taskCompletionSource.setResult(user);
            }
        }).executeAsync();

        return taskCompletionSource.getTask();
    }

    public static UserSession getCurrentSession() {
        return sessionInstance;
    }


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