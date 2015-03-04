package com.paypal.psix.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.paypal.psix.R;
import com.paypal.psix.fragments.OnboardingFragment;


public class OnboardingActivity extends FragmentActivity {

    private OnboardingFragment onboardingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
    }

}
