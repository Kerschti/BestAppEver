package com.example.kerstindittmann.bestappever;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.kerstindittmann.bestappever.ListenHelper.COL_NAME_DING;

public class SupermarktAuswahl extends AppCompatActivity {

    private SQLiteDatabase einkaufsliste;
    private ListView supermarketlist;
    private Cursor cursor;
    private int positionClick = -1;

    //Hashmap, über die der übergebene Artikel mit Key(Supermarkt) verbunden wird
    public static HashMap<String, String> supermarktMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkt_auswahl);

        //myVal von Eingabe in ListActivity holen
        final String myVal = getIntent().getExtras().getString("My_Key");

        supermarketlist = (ListView) findViewById(R.id.supermarktlist);


        //String array aus Ressources abfragen und mit Adapter in ListView anzeigen
        final String[] supermarkets = getResources().getStringArray(R.array.supermarkets);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item, R.id.lv_zutat, supermarkets);
        supermarketlist.setAdapter(aa);

        supermarketlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        supermarketlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //mit position arbeiten bei loeschen braucht man neuen button
            //mit position daten loeschen
            //neue Taste und Auswahl loeschen
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox box = (CheckBox) view.findViewById(R.id.checkBox2);
                box.setChecked(true);
                Log.i("CHECK BOX", "THIS WORKS");
                positionClick = position;
                //Zuwesiung von Wert zu Supermarkt
                switch (position) {
                    case 0:
                        supermarktMap.put(supermarkets[0], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[0] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        supermarktMap.put(supermarkets[1], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[1] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        supermarktMap.put(supermarkets[2], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[2] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        supermarktMap.put(supermarkets[3], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[3] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        supermarktMap.put(supermarkets[4], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[4] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        supermarktMap.put(supermarkets[5], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[5] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        supermarktMap.put(supermarkets[6], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[6] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        supermarktMap.put(supermarkets[7], myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal + " zu "+ supermarkets[7] + " hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    public void onZuerickClick(View view) {
        finish();
    }


}

