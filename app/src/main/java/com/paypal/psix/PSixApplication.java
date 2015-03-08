package com.paypal.psix;

import android.content.Context;
import com.activeandroid.app.Application;

public class PSixApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

}
