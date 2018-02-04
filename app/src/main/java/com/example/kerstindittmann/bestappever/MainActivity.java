package com.example.kerstindittmann.bestappever;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Set;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_CODE = 1;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;


    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    //should dialog for place be shown?
    private boolean isPlaceSupermarket = false;



    Task<PlaceLikelihoodBufferResponse> placeResult;

    private static final String TAG = "PLACE DETECTION";


    //Datenbank
    private SQLiteDatabase einkaufsListe;
    private EditText ding;


    TextView test;
    TextView test1;
    EditText zutat;
    public ImageButton apple = null;
    private ImageButton baguette = null;
    private ImageButton banana = null;
    private ImageButton beer = null;
    private ImageButton bread = null;
    private ImageButton coffeebeans = null;
    private ImageButton croissant = null;
    private ImageButton eggs = null;
    private ImageButton grapes = null;
    private ImageButton lettuce = null;
    private ImageButton milk = null;
    private ImageButton muffin = null;
    private ImageButton olives = null;
    private ImageButton orange = null;
    private ImageButton tomato = null;
    private ImageButton water = null;
    Button loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Zugriff auf Edittext Feld schaffen
        ding = (EditText) findViewById(R.id.zutat);

        //Zugriff auf Datenbank
        ListenHelper lis = ListenHelper.createInstance(this, "Einkaufsliste.db");
        einkaufsListe = lis.getWritableDatabase();

        apple = (ImageButton) findViewById(R.id.appel);
        apple.setOnClickListener(imgButtonHandler);

        baguette = (ImageButton) findViewById(R.id.baguette);
        baguette.setOnClickListener(imgButtonHandler);

        banana = (ImageButton) findViewById(R.id.banana);
        banana.setOnClickListener(imgButtonHandler);

        beer = (ImageButton) findViewById(R.id.beer);
        beer.setOnClickListener(imgButtonHandler);

        bread = (ImageButton) findViewById(R.id.bread);
        bread.setOnClickListener(imgButtonHandler);

        coffeebeans = (ImageButton) findViewById(R.id.coffeebeans);
        coffeebeans.setOnClickListener(imgButtonHandler);

        croissant = (ImageButton) findViewById(R.id.croissant);
        croissant.setOnClickListener(imgButtonHandler);

        eggs = (ImageButton) findViewById(R.id.eggs);
        eggs.setOnClickListener(imgButtonHandler);

        grapes = (ImageButton) findViewById(R.id.grape);
        grapes.setOnClickListener(imgButtonHandler);

        lettuce = (ImageButton) findViewById(R.id.lettuce);
        lettuce.setOnClickListener(imgButtonHandler);

        milk = (ImageButton) findViewById(R.id.milk);
        milk.setOnClickListener(imgButtonHandler);

        muffin = (ImageButton) findViewById(R.id.muffin);
        muffin.setOnClickListener(imgButtonHandler);

        olives = (ImageButton) findViewById(R.id.olives);
        olives.setOnClickListener(imgButtonHandler);

        orange = (ImageButton) findViewById(R.id.orange);
        orange.setOnClickListener(imgButtonHandler);

        tomato = (ImageButton) findViewById(R.id.tomato);
        tomato.setOnClickListener(imgButtonHandler);

        water = (ImageButton) findViewById(R.id.water);
        water.setOnClickListener(imgButtonHandler);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocationPermission();
        getDeviceLocation();
        //TODO add if(currentPlace==placeWhereToBuy)
        showCurrentPlace();

    }

    /*@Override
    public void onResume(){
        super.onResume();
        getLocationPermission();
        getDeviceLocation();
        //TODO add if(currentPlace==placeWhereToBuy)
        showCurrentPlace();

    }*/


    public void changeActiv(View view) {
        Intent myIntent = new Intent(this, GPSLocation2.class);
        startActivity(myIntent);
    }

    View.OnClickListener imgButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.appel:
                    apple.setBackgroundResource(R.drawable.apple1);
                    Toast.makeText(getApplicationContext(), "+ Apfel" , Toast.LENGTH_SHORT).show();

                    break;
                case R.id.baguette:
                    baguette.setBackgroundResource(R.drawable.baguette1);
                    Toast.makeText(getApplicationContext(), "+ Baguette" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.banana:
                    banana.setBackgroundResource(R.drawable.banana1);
                    Toast.makeText(getApplicationContext(), "+ Banane" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.beer:
                    beer.setBackgroundResource(R.drawable.beer1);
                    Toast.makeText(getApplicationContext(), "+ Bier" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bread:
                    bread.setBackgroundResource(R.drawable.bread1);
                    Toast.makeText(getApplicationContext(), "+ Brot" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.coffeebeans:
                    coffeebeans.setBackgroundResource(R.drawable.coffeebeans1);
                    Toast.makeText(getApplicationContext(), "+ Kaffee" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.croissant:
                    croissant.setBackgroundResource(R.drawable.croissant1);
                    Toast.makeText(getApplicationContext(), "+ Croissant" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.eggs:
                    eggs.setBackgroundResource(R.drawable.eggs1);
                    Toast.makeText(getApplicationContext(), "+ Eier" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.grape:
                    grapes.setBackgroundResource(R.drawable.grapes1);
                    Toast.makeText(getApplicationContext(), "+ Trauben" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lettuce:
                    lettuce.setBackgroundResource(R.drawable.lettuce1);
                    Toast.makeText(getApplicationContext(), "+ Salat" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.milk:
                    milk.setBackgroundResource(R.drawable.milk1);
                    Toast.makeText(getApplicationContext(), "+ Milch" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.muffin:
                    muffin.setBackgroundResource(R.drawable.muffin1);
                    Toast.makeText(getApplicationContext(), "+ Muffin" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.olives:
                    olives.setBackgroundResource(R.drawable.olives1);
                    Toast.makeText(getApplicationContext(), "+ Oliven" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.orange:
                    orange.setBackgroundResource(R.drawable.orange1);
                    Toast.makeText(getApplicationContext(), "+ Orangen" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tomato:
                    tomato.setBackgroundResource(R.drawable.tomato1);
                    Toast.makeText(getApplicationContext(), "+ Tomaten" + "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.water:
                    water.setBackgroundResource(R.drawable.water1);
                    Toast.makeText(getApplicationContext(), "+ Wasser" + "+", Toast.LENGTH_SHORT).show();
                    break;

            }

        }

    };


     @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getLocation(View view) {
        getLocationPermission();
        getDeviceLocation();

       // Toast.makeText(this, ""+placeResult.getResult().toString(), Toast.LENGTH_SHORT).show();

        showCurrentPlace();
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());

                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void showCurrentPlace() {


        if (mLocationPermissionGranted) {
            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final
            Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener
                    (new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

                                // Set the count, handling cases where less than 5 entries are returned.
                                int count;
                                if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                    count = likelyPlaces.getCount();
                                } else {
                                    count = M_MAX_ENTRIES;
                                }

                                int i = 0;
                                mLikelyPlaceNames = new String[count];
                                mLikelyPlaceAddresses = new String[count];
                                mLikelyPlaceAttributions = new String[count];
                                mLikelyPlaceLatLngs = new LatLng[count];

                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                    // Build a list of likely places to show the user.
                                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                                    mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
                                            .getAddress();
                                    mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
                                            .getAttributions();
                                    mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
                                    Log.i(TAG, mLikelyPlaceNames[i]);

                                    i++;
                                    if (i > (count - 1)) {
                                        break;
                                    }
                                }

                                // Release the place likelihood buffer, to avoid memory leaks.
                                likelyPlaces.release();

                                // Show a dialog offering the user the list of likely places, and add a
                                // marker at the selected place.
                                //TODO add something that checks if supermarket
                                //openPlacesDialog();


                            } else {
                                Log.e(TAG, "Exception: %s", task.getException());
                            }
                        }
                    });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");


            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    /**
     * Displays a form allowing the user to select a place from a list of likely places.
     */
    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The "which" argument contains the position of the selected item.
                //TODO might be irrelevant
                /*LatLng markerLatLng = mLikelyPlaceLatLngs[which];
                String markerSnippet = mLikelyPlaceAddresses[which];
                if (mLikelyPlaceAttributions[which] != null) {
                    markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which];
                }*/

            }
        };

        // Display the dialog.
        String msg = "";
        for(int i = 0; i<M_MAX_ENTRIES; i++){
            Log.i(TAG, mLikelyPlaceNames[i]);
            if(mLikelyPlaceNames[i].equals("CineStar")) {
                isPlaceSupermarket = true;
                msg = mLikelyPlaceNames[i];
                Log.i(TAG, msg + "HALLO");
            }
        }
        if(isPlaceSupermarket) {
            //TODO alter for showing article to buy and place
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Sam sagt:")
                    .setMessage(msg)
                    //.setItems(mLikelyPlaceNames[i], listener)
                    .setNeutralButton("Danke", listener)
                    .show();

        }
    }

    public void auflistenClick(View view) {
        //Sprung in zweite Activity
        Intent intent = new Intent();
        intent.setClass(this, ListActivity.class);
        startActivity(intent);
    }

    public void speichernClick(View view) {
        ContentValues neuesDing = new ContentValues();
        neuesDing.put(ListenHelper.COL_NAME_DING, ding.getText().toString());

        einkaufsListe.insert(ListenHelper.TABLE_NAME_EINKAUFSLISTE, null, neuesDing);
    }

}
