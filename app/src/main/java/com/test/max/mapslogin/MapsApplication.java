package com.test.max.mapslogin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        FacebookSdk.sdkInitialize(this);
        ParseFacebookUtils.initialize(this);
    }
}
