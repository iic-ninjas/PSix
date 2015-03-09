package com.paypal.psix.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.paypal.psix.fragments.OnboardingFragment;


public class OnboardingActivity extends FragmentActivity {

    private OnboardingFragment mainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new OnboardingFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (OnboardingFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }

}