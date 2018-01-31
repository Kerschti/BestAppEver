package com.example.kerstindittmann.bestappever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView test;
    TextView test1;
    EditText zutat;
    Button loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void changeActiv(View view) {
        Intent myIntent = new Intent(view.getContext(), GPSLocation2.class);
        startActivity(myIntent);
    }



}
