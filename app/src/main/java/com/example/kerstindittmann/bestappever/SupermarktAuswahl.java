package com.example.kerstindittmann.bestappever;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SupermarktAuswahl extends AppCompatActivity {

    ListView supermarketlist;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkt_auswahl);

        supermarketlist = (ListView) findViewById(R.id.supermarktlist);

        String[] supermarkets = getResources().getStringArray(R.array.supermarkets);
        //Mapping Adapters
        String[] anzeigeSpalten = getResources().getStringArray(R.array.supermarkets);
        int[] anzeigeViews = new int[]{R.id.lv_zutat};

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item,
                R.id.lv_zutat, supermarkets);
        supermarketlist.setAdapter(aa);
    }
}
