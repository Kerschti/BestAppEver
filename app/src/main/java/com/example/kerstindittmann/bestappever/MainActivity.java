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

import java.util.Arrays;
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

    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private int placeCount;
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
    private ImageButton apple = null;
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

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        //Zugriff auf Edittext Feld schaffen
        ding = (EditText) findViewById(R.id.zutat);

        //Actionbar aendern
        setTitle("Shopping with Sam");
        //getActionBar().setIcon(R.drawable.beer);


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

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mLikelyPlaceNames != null) {
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


   @Override
    public void onResume(){
        super.onResume();
       apple.setBackgroundResource(R.drawable.apple);
       baguette.setBackgroundResource(R.drawable.baguette);
       banana.setBackgroundResource(R.drawable.banana);
       beer.setBackgroundResource(R.drawable.beer);
       bread.setBackgroundResource(R.drawable.bread);
       coffeebeans.setBackgroundResource(R.drawable.coffeebeans);
       croissant.setBackgroundResource(R.drawable.croissant);
       eggs.setBackgroundResource(R.drawable.eggs);
       grapes.setBackgroundResource(R.drawable.grapes);
       lettuce.setBackgroundResource(R.drawable.lettuce);
       milk.setBackgroundResource(R.drawable.milk);
       muffin.setBackgroundResource(R.drawable.muffin);
       olives.setBackgroundResource(R.drawable.olives);
       orange.setBackgroundResource(R.drawable.orange);
       tomato.setBackgroundResource(R.drawable.tomato);
       water.setBackgroundResource(R.drawable.water);
       //check permission
        getLocationPermission();
        //get location
        getDeviceLocation();
        //if currentPlace == supermaket show alert
        showCurrentPlace();

    }

    public void changeActiv(View view) {
        Intent myIntent = new Intent(this, GPSLocation2.class);
        startActivity(myIntent);
    }

    View.OnClickListener imgButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ContentValues neuesDing = new ContentValues();

            switch (v.getId()) {
                case R.id.appel:
                    apple.setBackgroundResource(R.drawable.apple1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Apfel");
                    speichern(neuesDing);
                    break;
                case R.id.baguette:
                    baguette.setBackgroundResource(R.drawable.baguette1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Baguette");
                    speichern(neuesDing);
                    break;
                case R.id.banana:
                    banana.setBackgroundResource(R.drawable.banana1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Banane");
                    speichern(neuesDing);
                    break;
                case R.id.beer:
                    beer.setBackgroundResource(R.drawable.beer1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Bier");
                    speichern(neuesDing);
                    break;
                case R.id.bread:
                    bread.setBackgroundResource(R.drawable.bread1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Brot");
                    speichern(neuesDing);
                    break;
                case R.id.coffeebeans:
                    coffeebeans.setBackgroundResource(R.drawable.coffeebeans1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Kaffee");
                    speichern(neuesDing);
                    break;
                case R.id.croissant:
                    croissant.setBackgroundResource(R.drawable.croissant1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Croissant");
                    speichern(neuesDing);
                    break;
                case R.id.eggs:
                    eggs.setBackgroundResource(R.drawable.eggs1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Eier");
                    speichern(neuesDing);
                    break;
                case R.id.grape:
                    grapes.setBackgroundResource(R.drawable.grapes1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Trauben");
                    speichern(neuesDing);
                    break;
                case R.id.lettuce:
                    lettuce.setBackgroundResource(R.drawable.lettuce1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Salat");
                    speichern(neuesDing);
                    break;
                case R.id.milk:
                    milk.setBackgroundResource(R.drawable.milk1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Milch");
                    speichern(neuesDing);
                    break;
                case R.id.muffin:
                    muffin.setBackgroundResource(R.drawable.muffin1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Muffin");
                    speichern(neuesDing);
                    break;
                case R.id.olives:
                    olives.setBackgroundResource(R.drawable.olives1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Oliven");
                    speichern(neuesDing);
                    break;
                case R.id.orange:
                    orange.setBackgroundResource(R.drawable.orange1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Orangen");
                    speichern(neuesDing);
                    break;
                case R.id.tomato:
                    tomato.setBackgroundResource(R.drawable.tomato1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Tomaten");
                    speichern(neuesDing);
                    break;
                case R.id.water:
                    water.setBackgroundResource(R.drawable.water1);
                    neuesDing.put(ListenHelper.COL_NAME_DING, "Wasser");
                    speichern(neuesDing);
                    break;

            }

        }

    };


     @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    //TODO Cut one of the permission things
   /* @Override
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
    }*/

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

                                if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                    placeCount = likelyPlaces.getCount();
                                } else {
                                    placeCount = M_MAX_ENTRIES;
                                }

                                int i = 0;
                                mLikelyPlaceNames = new String[placeCount];
                                mLikelyPlaceAddresses = new String[placeCount];
                                mLikelyPlaceAttributions = new String[placeCount];
                                mLikelyPlaceLatLngs = new LatLng[placeCount];

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
                                    if (i > (placeCount - 1)) {
                                        break;
                                    }
                                }

                                // Release the place likelihood buffer, to avoid memory leaks.
                                likelyPlaces.release();

                                // Show a dialog offering the user the list of likely places, and add a
                                // marker at the selected place.

                                openPlacesDialog();


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



        String[] supermarkets = getResources().getStringArray(R.array.supermarkets);
        String msg = "";
        String atr = "";
        for(int i = 0; i<placeCount; i++){
            Log.i(TAG, mLikelyPlaceNames[i]);
            if(SupermarktAuswahl.supermarktMap.containsKey(mLikelyPlaceNames[i])) {
                isPlaceSupermarket = true;
                msg = mLikelyPlaceNames[i];
                atr = SupermarktAuswahl.supermarktMap.get(mLikelyPlaceNames[i]);
                Log.i(TAG, msg + "HALLO");
            }
        }
        // Display the dialog.
        if(isPlaceSupermarket) {
            //TODO alter for showing article to buy and place
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Sam sagt:")
                    .setMessage("Du bist bei "+msg+"! Hier musst du noch " + atr+" shoppen!")
                    //.setItems(mLikelyPlaceNames[i], listener)
                    .setNeutralButton("Danke", listener)
                    .show();

        }
    }

    public void speichern(ContentValues n){
        einkaufsListe.insert(ListenHelper.TABLE_NAME_EINKAUFSLISTE, null, n);
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
