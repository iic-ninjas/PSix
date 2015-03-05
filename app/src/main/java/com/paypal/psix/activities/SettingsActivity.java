package com.paypal.psix.activities;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paypal.psix.R;
import com.paypal.psix.utils.EmailValidator;

public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    Preference paypalPref;
    Preference signOutPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        setupToolbar();
        setupPaypalAccount();
        setupSignOut();
    }

    private void setupToolbar() {
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar)LayoutInflater.from(this).inflate(R.layout.settings_bar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == paypalPref) {
            return validatePaypalAccount(String.valueOf(newValue));
        }
        return true;
    }

    private void setupPaypalAccount() {
        paypalPref = findPreference(getString(R.string.pref_paypal_key));
        paypalPref.setOnPreferenceChangeListener(this);
    }

    private void setupSignOut() {
        signOutPref = findPreference(getString(R.string.pref_signout_key));
        signOutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference pref) {
                signOut();
                return true;
            }
        });
    }

    private boolean validatePaypalAccount(String email) {
        boolean isValid = EmailValidator.validate(email);
        if (!isValid) {
            Toast.makeText(SettingsActivity.this, getString(R.string.toast_invalid_email), Toast.LENGTH_LONG).show();
        }
        return isValid;
    }

    private void signOut() {
        Toast.makeText(SettingsActivity.this, getString(R.string.toast_sign_out), Toast.LENGTH_SHORT).show();
    }

}