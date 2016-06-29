package com.greenapex.mightyhomeplanz;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by admin on 31-May-16.by ${COMPUTERNAME}
 */
public class GreenApexApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
