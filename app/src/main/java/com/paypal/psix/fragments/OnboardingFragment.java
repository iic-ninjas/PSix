package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.paypal.psix.R;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class OnboardingFragment extends Fragment {

    private static final String LOG_TAG = OnboardingFragment.class.getSimpleName();
    private UiLifecycleHelper fbLifecycleHelper;
    @InjectView(R.id.fb_login_button) LoginButton fbLoginButton;

    public OnboardingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);
        ButterKnife.inject(this, rootView);

        fbLoginButton.setReadPermissions("public_profile");

        return rootView;
    }

}