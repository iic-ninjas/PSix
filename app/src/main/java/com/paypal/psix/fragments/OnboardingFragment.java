package com.paypal.psix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.paypal.psix.R;
import com.paypal.psix.activities.EventsActivity;
import com.paypal.psix.activities.OnboardingActivity;
import com.paypal.psix.services.UserSession;

import java.util.Arrays;
import java.util.List;


public class OnboardingFragment extends Fragment {

    private static final String LOG_TAG = OnboardingFragment.class.getSimpleName();
    private static final List<String> PERMISSIONS = Arrays.asList("public_profile", "user_events");

    private LoginButton loginButton;
    private UiLifecycleHelper uiHelper;
    private GraphUser user;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception e) {
            onSessionStateChange(session, state, e);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle bundle) {
            Log.d(LOG_TAG, "Success!");
        }

        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception e, Bundle bundle) {
            Log.d(LOG_TAG, String.format("Error: %s", e.toString()));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        uiHelper = new UiLifecycleHelper(this.getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);

        loginButton = (LoginButton) view.findViewById(R.id.authButton);
        loginButton.setReadPermissions(PERMISSIONS);
        loginButton.setFragment(this);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser graphUser) {
                user = graphUser;
                updateUserData();
            }
        });

        return view;
    }

    private void updateUserData() {
        if (user != null) {
            Log.d(LOG_TAG, "User logged in");
            UserSession.setUser(user);
            this.getActivity().finish();
            this.getActivity().startActivity(
                    new Intent(this.getActivity(), EventsActivity.class)
            );
        } else {
            Log.d(LOG_TAG, "Current user is null");
            if (UserSession.isUserSignedIn()) {
                UserSession.userLoggedOut();
                Log.d(LOG_TAG, "User logged out");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception e) {}

}