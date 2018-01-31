package com.example.kerstindittmann.bestappever;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GPSLocation2 extends AppCompatActivity {

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters

    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    public static final int REQUEST_CODE = 1;

    protected LocationManager locationManager;

    protected Button retrieveLocationButton;

    //  @TargetApi(Build.VERSION_CODES.M)

    @Override

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gpslocation2);

        retrieveLocationButton = (Button) findViewById(R.id.retrieve_location_button);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "NO PERMISSION", Toast.LENGTH_LONG);
            requestPermissions( new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            locationManager.requestLocationUpdates(

                    LocationManager.GPS_PROVIDER,

                    MINIMUM_TIME_BETWEEN_UPDATES,

                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,

                    new GPSLocation2.MyLocationListener()

            );
        }

        retrieveLocationButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                showCurrentLocation();

            }

        });

    }

    protected void showCurrentLocation() {


        if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "NO PERMISSION", Toast.LENGTH_LONG);
            requestPermissions( new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            if (location != null) {

                String message = String.format(

                        "Current Location \n Longitude: %1$s \n Latitude: %2$s",

                        location.getLongitude(), location.getLatitude()

                );

                Toast.makeText(GPSLocation2.this, message,

                        Toast.LENGTH_LONG).show();

            }
        }

    }


    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {

            String message = String.format(

                    "New Location \n Longitude: %1$s \n Latitude: %2$s",

                    location.getLongitude(), location.getLatitude()

            );

            Toast.makeText(GPSLocation2.this, message, Toast.LENGTH_LONG).show();

        }

        public void onStatusChanged(String s, int i, Bundle b) {

            Toast.makeText(GPSLocation2.this, "Provider status changed",

                    Toast.LENGTH_LONG).show();

        }

        public void onProviderDisabled(String s) {

            Toast.makeText(GPSLocation2.this,

                    "Provider disabled by the user. GPS turned off",

                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {

            Toast.makeText(GPSLocation2.this,

                    "Provider enabled by the user. GPS turned on",

                    Toast.LENGTH_LONG).show();

        }

    }

}

