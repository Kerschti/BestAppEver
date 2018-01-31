package com.example.kerstindittmann.bestappever;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.PlaceDetectionClient;
//import com.google.android.gms.location.places.PlaceLikelihood;
//import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;


//implements GoogleApiClient.OnConnectionFailedListener

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;


    //private GoogleApiClient mGoogleApiClient;

    //PlaceDetectionClient mPlaceDetectionClient;
    //Task<PlaceLikelihoodBufferResponse> placeResult;

    private static final String TAG = "PLACE DETECTION";


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

        final ImageButton apple = (ImageButton) findViewById(R.id.appel);
        final ImageButton baguette;
        //baguette = (ImageButton) findViewById(R.id.baguette).setOnClickListener(buttonOnClick);
        final ImageButton banana = (ImageButton) findViewById(R.id.banana);
        final ImageButton beer = (ImageButton) findViewById(R.id.beer);
        final ImageButton bread = (ImageButton) findViewById(R.id.bread);
        final ImageButton coffeebeans = (ImageButton) findViewById(R.id.coffeebeans);
        final ImageButton croissant = (ImageButton) findViewById(R.id.croissant);
        final ImageButton eggs = (ImageButton) findViewById(R.id.eggs);
        final ImageButton grapes = (ImageButton) findViewById(R.id.grape);
        final ImageButton lettuce = (ImageButton) findViewById(R.id.lettuce);
        final ImageButton milk = (ImageButton) findViewById(R.id.milk);
        final ImageButton muffin = (ImageButton) findViewById(R.id.muffin);
        final ImageButton olives = (ImageButton) findViewById(R.id.olives);
        final ImageButton orange = (ImageButton) findViewById(R.id.orange);
        final ImageButton tomato = (ImageButton) findViewById(R.id.tomato);
        final ImageButton water = (ImageButton) findViewById(R.id.water);


    }


        //  mGoogleApiClient = new GoogleApiClient
        //          .Builder(this)
        //          .addApi(Places.GEO_DATA_API)
        //          .addApi(Places.PLACE_DETECTION_API)
        //          .enableAutoManage(this, this)
        //          .build();

        // mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

       /* if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "NO PERMISSION", Toast.LENGTH_LONG);
            requestPermissions( new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            mPlaceDetectionClient.getCurrentPlace(null);
        }

        placeResult = mPlaceDetectionClient.getCurrentPlace(null);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));
                }
                likelyPlaces.release();
            }
        });*/




    public void changeActiv(View view) {
        Intent myIntent = new Intent(this, GPSLocation2.class);
        startActivity(myIntent);
    }

    public View.OnClickListener buttonOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()){
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




    // @Override
   // public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    //}

    //public void getLocation(View view) {
     //   Toast.makeText(this, ""+placeResult.getResult().toString(), Toast.LENGTH_SHORT).show();
    //}
}
