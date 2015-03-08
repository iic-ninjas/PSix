package com.paypal.psix.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.paypal.psix.R;
import com.paypal.psix.fragments.OnboardingFragment;


public class OnboardingActivity extends FragmentActivity {

    private static final String LOG_TAG = OnboardingActivity.class.getSimpleName();
    private LoginButton loginButton;
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            onSessionStateChange(session, sessionState, e);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser graphUser) {
                Log.i(LOG_TAG, "user info fetched");
                if (graphUser != null) {
                    Log.i(LOG_TAG, "User logged in: " + graphUser.getFirstName() + " " + graphUser.getLastName());
                }
            }
        });
    }

    private void onSessionStateChange(Session session, SessionState state, Exception e) {}

}