package com.paypal.psix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.paypal.psix.R;
import com.paypal.psix.services.UserSession;

public class Splash extends Activity {

    private final String LOG_TAG = Splash.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Class<?> nextActivity = UserSession.isUserSignedIn() ?
                        EventsActivity.class : OnboardingActivity.class;
                Intent nextIntent = new Intent(Splash.this, nextActivity);
                Splash.this.startActivity(nextIntent);
                Splash.this.finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}