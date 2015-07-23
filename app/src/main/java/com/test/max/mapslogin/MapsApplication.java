package com.test.max.mapslogin;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by Max on 7/23/2015.
 */
public class MapsApplication extends Application {

    private final static String TAG = MapsApplication.class.getCanonicalName();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();

        ParseObject.registerSubclass(User.class);
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }
}
