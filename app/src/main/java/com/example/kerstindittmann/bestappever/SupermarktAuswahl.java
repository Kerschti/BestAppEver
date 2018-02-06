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

public class SupermarktAuswahl extends AppCompatActivity {

    private SQLiteDatabase einkaufsliste;
    ListView supermarketlist;
    Cursor cursor;
    private int positionClick = -1;
    ArrayList<String> edeka;
    ArrayList<String> rewe;
    ArrayList<String> aldi;
    ArrayList<String> lidel;
    ArrayList<String> tegut;
    ArrayList<String> denn;
    ArrayList<String> hsfulda;


    ListActivity liste = new ListActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkt_auswahl);

        String myVal = getIntent().getExtras().getString("My_Key");

        Toast.makeText(SupermarktAuswahl.this, myVal, Toast.LENGTH_SHORT).show();
        //String myValToString = Integer.toString(myVal);

        final ListenHelper lis = ListenHelper.createInstance(this, "Einkaufsliste.db");
        einkaufsliste = lis.getReadableDatabase();

        supermarketlist = (ListView) findViewById(R.id.supermarktlist);

        String[] supermarkets = getResources().getStringArray(R.array.supermarkets);
        //Mapping Adapters
        String[] anzeigeSpalten = getResources().getStringArray(R.array.supermarkets);
        int[] anzeigeViews = new int[]{R.id.lv_zutat};

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item,
                R.id.lv_zutat, supermarkets);
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
                //Toast.makeText(ListActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                positionClick = position;

            }

        });
    /*

        //Welche Spalte soll ausgegeben werden
        String[] projection = {
                COL_NAME_ID,
                ListenHelper.COL_NAME_DING
        };


        //Datenbankabfrage
        cursor = einkaufsliste.query(ListenHelper.TABLE_NAME_EINKAUFSLISTE,
                projection, "1=1", null, null, null, null);
*/

/*        String workAround = "SELECT ding FROM liste WHERE _id = ?";
        cursor = einkaufsliste.rawQuery(workAround, new String[]{myVal});
        if (cursor.moveToFirst()){
            do {
                // Passing values
                String ding = cursor.getString(1);
                Toast.makeText(this, ding, Toast.LENGTH_SHORT).show();
                // Do something Here with values
            } while(cursor.moveToNext());
        }
        cursor.close();


        if(positionClick != -1) {
            cursor.moveToPosition(Integer.valueOf(myVal));
        }*/


    }

    public void onZuerickClick(View view) {
        finish();
    }

    public void supermarktSpeichern(View view) {



        int id = 0;
        if(positionClick != -1){
            cursor.moveToPosition(positionClick);
            id = cursor.getInt(0);
        }
        Toast.makeText(SupermarktAuswahl.this, ""+id, Toast.LENGTH_SHORT).show();



    }
}

