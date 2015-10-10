package com.test.max.mapslogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Max on 7/23/2015.
 */
public class DispatchLaunchActivity extends Activity {
    private final static String TAG = DispatchLaunchActivity.class.getCanonicalName();
    private ParseUser currentUser;

    public DispatchLaunchActivity(){
        // Empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");

        currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            ParseLoginBuilder builder = new ParseLoginBuilder(this);
            startActivityForResult(builder.build(), 0);
        } else {
            launchMap();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult()");
        super.onActivityResult(requestCode, resultCode, data);

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            Log.d(TAG, "Login successful");
            launchMap();
        }
    }

    private void launchMap(){
        Intent launchIntent = new Intent(this, MapsActivity.class);
//        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launchIntent);
        finish();
    }
}
