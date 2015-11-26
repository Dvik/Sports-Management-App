package com.example.divya.sepm;

import android.content.IntentSender;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divya.helpers.AppConfig;
import com.example.divya.models.Event;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class EventOnMapActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = FragmentActivity.class.getSimpleName();
    private Marker myMarker;
    List<Event> eventsListMap;

    TextView tt1,tt2,tt3,tt4,tt5;
    TextView personAge;
    ImageView img;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_on_map);
        mToolbar = (Toolbar) findViewById(R.id.appr_details_toolbar);
        mToolbar.setTitle("Events");
       tt1 = (TextView) findViewById(R.id.event_subject_tv);
       tt2 = (TextView) findViewById(R.id.event_date);

       tt3 = (TextView) findViewById(R.id.event_time);
       tt4 = (TextView) findViewById(R.id.event_place);

       img = (ImageView) findViewById(R.id.event_image);

        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
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
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        int i;
        eventsListMap = AppConfig.listOfEvents;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(26.933025, 75.923163), 16));

        for(i=0; i<eventsListMap.size();i++) {
            Marker marker1 = mMap.addMarker(new MarkerOptions().position(
                    new LatLng(Double.parseDouble(eventsListMap.get(i).lat), Double.parseDouble(eventsListMap.get(i).lng)))
                    .title(eventsListMap.get(i).name));
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Toast.makeText(getApplicationContext(), point.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                int j;
                for(j=0; j<eventsListMap.size();j++) {
                    Event events = eventsListMap.get(j);

                    if (arg0.getTitle().equals(events.name)) {

                        tt1.setText((String) events.name);
                        tt2.setText((String) events.date);
                        tt3.setText((String) events.start_time+"   "+(String) events.end_time);
                        tt2.setText((String) events.lat+"   "+(String) events.lng);

                        String sport =  (String) events.sport;

                        if(sport.equalsIgnoreCase("Football")) {
                            img.setImageResource(R.drawable.football);
                        }

                        if(sport.equalsIgnoreCase("Cricket")) {
                           img.setImageResource(R.drawable.basketball);
                        }

                        if(sport.equalsIgnoreCase("basketball")) {
                           img.setImageResource(R.drawable.iconspo);
                        }

                        if(sport.equalsIgnoreCase("table tennis")) {
                            img.setImageResource(R.drawable.tabletennis);
                        }


                        if(sport.equalsIgnoreCase("boxing")) {
                            img.setImageResource(R.drawable.boxing);
                        }


                    }
                }
                return true;
            }

        });
       /* Location location = mMap.getMyLocation();

        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("It's Me!"));

*/
/*
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(41.889, -87.622), 16));

        // You can customize the marker image using images bundled with
        // your app, or dynamically generated bitmaps.
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_event_black_24dp))
                .anchor(0.0f, 1.0f)

                .position(new LatLng(41.889, -87.622)));
*/

    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

   /* @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }*/

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);


        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        myMarker = mMap.addMarker(options);

/*
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
*/




    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }
}
