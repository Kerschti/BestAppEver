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

import static com.example.kerstindittmann.bestappever.ListenHelper.COL_NAME_DING;

public class SupermarktAuswahl extends AppCompatActivity {

    private SQLiteDatabase einkaufsliste;
    ListView supermarketlist;
    Cursor cursor;
    String theName;
    private int positionClick = -1;
    ArrayList<String> edeka;
    ArrayList<String> rewe;
    ArrayList<String> aldi;
    ArrayList<String> lidl;
    ArrayList<String> tegut;
    ArrayList<String> denn;
    ArrayList<String> kaufland;

    ArrayList<String> hsfulda;


    ListActivity liste = new ListActivity();


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

        final String myVal = getIntent().getExtras().getString("My_Key");

        Toast.makeText(SupermarktAuswahl.this,"huhuhuh"+ myVal, Toast.LENGTH_SHORT).show();
        //String myValToString = Integer.toString(myVal);

        final ListenHelper lis = ListenHelper.createInstance(this, "Einkaufsliste.db");
        einkaufsliste = lis.getReadableDatabase();

        supermarketlist = (ListView) findViewById(R.id.supermarktlist);

        String[] supermarkets = getResources().getStringArray(R.array.supermarkets);
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
                CheckBox box = (CheckBox)view.findViewById(R.id.checkBox2);
                box.setChecked(true);
                Log.i("CHECK BOX", "THIS WORKS");
                positionClick = position;
                Toast.makeText(SupermarktAuswahl.this, "index"+position, Toast.LENGTH_SHORT).show();

                switch(position){
                    case 0 :  rewe.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Rewe hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 1 : kaufland.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Kaufland hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 2 : aldi.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Aldi hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 3 : lidl.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Lidl hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 4 : edeka.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Edeka hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 5 : tegut.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Tegut hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 6 : denn.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu Denn hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                    case 7: hsfulda.add(myVal);
                        Toast.makeText(SupermarktAuswahl.this, myVal+ "zu hsFulda hinzugefügt", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

        });

    }

    public void onZuerickClick(View view) {
        finish();
    }


}

