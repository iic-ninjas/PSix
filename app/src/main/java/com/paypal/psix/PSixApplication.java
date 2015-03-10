package com.paypal.psix;

import android.content.Context;

import com.activeandroid.app.Application;
import com.facebook.Session;

public class PSixApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        Session.openActiveSessionFromCache(appContext);
    }

    public static Context getAppContext() {
        return appContext;
    }

}
