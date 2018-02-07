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
    ListView supermarketlist;
    Cursor cursor;
    private int positionClick = -1;
    ArrayList<String> edeka;
    ArrayList<String> rewe;
    ArrayList<String> aldi;
    ArrayList<String> lidl;
    ArrayList<String> tegut;
    ArrayList<String> denn;
    ArrayList<String> kaufland;
    ArrayList<String> hsfulda;

    public static HashMap<String, String> supermarktMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkt_auswahl);

        kaufland = new ArrayList<String>();
        edeka = new ArrayList<String>();
        rewe = new ArrayList<String>();
        aldi = new ArrayList<String>();
        lidl = new ArrayList<String>();
        tegut = new ArrayList<String>();
        denn = new ArrayList<String>();
        hsfulda = new ArrayList<String>();

        //myVal von Eingabe in ListActivity holen
        final String myVal = getIntent().getExtras().getString("My_Key");

        final ListenHelper lis = ListenHelper.createInstance(this, "Einkaufsliste.db");
        einkaufsliste = lis.getReadableDatabase();

        supermarketlist = (ListView) findViewById(R.id.supermarktlist);

        final String[] supermarkets = getResources().getStringArray(R.array.supermarkets);

        //Mapping Adapters
        String[] anzeigeSpalten = getResources().getStringArray(R.array.supermarkets);
        int[] anzeigeViews = new int[]{R.id.lv_zutat};

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

