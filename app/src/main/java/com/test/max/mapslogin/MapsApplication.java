package com.test.max.mapslogin;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by Max on 7/23/2015.
 */
public class MapsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(User.class);
        Parse.initialize(this, "7tfLj9j30P3Ldq4GMDBYy4lzyJkBTBCui6MtMr88", "vw41Pbx5ODstT8db6UhlE7rlbgu1OEhE4wt1KqSW");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        
    }
}
