package com.test.max.mapslogin;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // TAG for using Logs
    private final static String TAG = MapsActivity.class.getCanonicalName();

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    /**
     * The Google API Client provides a common entry point to all the Google Play services
     * and manages the network connection between the user's device and each Google service.
     */
    private GoogleApiClient mGoogleApiClient;

    // Variable for storing location of the device
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        buildGoogleApiClient();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
        Log.d(TAG, "Connecting Google Api Client");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        Log.d(TAG, "Disconnecting Google Api Client");
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        Log.d(TAG, "setUpMapIfNeeded()");
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            Log.d(TAG, "mMap is null");
            // Try to obtain the map from the SupportMapFragment.
            Log.d(TAG, "Try to obtain the map from the SupportMapFragment");
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                Log.d(TAG, "Success in obtaining the map");

                // Enable / Disable toolbar (navigation and map buttons in the right bottom corner)
                mMap.getUiSettings().setMapToolbarEnabled(false);

//                setUpMap();
            }
        } else {
            Log.d(TAG, "mMap is not null");
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Log.d(TAG, "setUpMap()");
        mMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))/*.title("Marker")*/);
    }

    // Method for animating the camera to the current position of the device
    private void centerMapOnMyLocation() {
        Log.d(TAG, "centerMapOnMyLocation()");
        if (mMap != null && mLastLocation != null) {
            Log.d(TAG, "mMap and mLastLocation are not null");
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))      // Sets the center of the map to location user
                    .zoom(14)                   // Sets the zoom (was 14)
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Log.d(TAG, "Animating camera to current position");
        } else {
            Log.d(TAG, "mMap or/and mLastLocation is null");
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.d(TAG, "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
        Log.d(TAG, "Connected to Google Play services");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d(TAG, "Location: " + mLastLocation);
        setUpMap();
        centerMapOnMyLocation();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.
        Log.d(TAG, "Connection failed, error code: " + result.getErrorCode());
    }
}
