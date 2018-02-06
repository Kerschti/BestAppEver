package com.example.kerstindittmann.bestappever;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.kerstindittmann.bestappever.ListenHelper.COL_NAME_ID;


public class ListActivity extends AppCompatActivity {

    private SQLiteDatabase einkaufsliste;
    ListView dbListe;
    CheckBox check;
    private int positionClick = 0;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbListe = (ListView) findViewById(R.id.db_liste);
        check = (CheckBox) findViewById(R.id.checkBox2);

        //Welche Spalte soll ausgegeben werden
        String[] projection = {
                ListenHelper.COL_NAME_ID,
                ListenHelper.COL_NAME_DING
        };

        //Zugriff auf Datenbank
        final ListenHelper lis = ListenHelper.createInstance(this, "Einkaufsliste.db");
        einkaufsliste = lis.getWritableDatabase();

        //Datenbankabfrage
        cursor = einkaufsliste.query(ListenHelper.TABLE_NAME_EINKAUFSLISTE,
                projection, "1=1", null, null, null, null);

        //Mapping Adapters
        String[] anzeigeSpalten = new String[]{
                ListenHelper.COL_NAME_DING};
        int[] anzeigeViews = new int[]{R.id.lv_zutat};

        //Adapter bauen
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, anzeigeSpalten, anzeigeViews, 0);
        dbListe.setAdapter(adapter);

        //auswaehlen
        dbListe.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        dbListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //mit position arbeiten bei loeschen braucht man neuen button
            //mit position daten loeschen
            //neue Taste und Auswahl loeschen
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox box = (CheckBox)view.findViewById(R.id.checkBox2);
                box.setChecked(true);
                Log.i("CHECK BOX", "THIS WORKS");
                Toast.makeText(ListActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                positionClick = position;
                cursor.moveToPosition(positionClick);

                //String theName = cursor.getString(cursor.getColumnIndex("COL_NAME_DING"));
                //Toast.makeText(ListActivity.this, ""+theName, Toast.LENGTH_SHORT).show();
                //Toast.makeText(ListActivity.this, ""+position, Toast.LENGTH_SHORT).show();


            }

        });}

    public void onZuerickClick(View view){
        loeschen();
        finish();
    }


    public void loeschen(){
        if(positionClick != -1){

            cursor.moveToPosition(positionClick);
            int id = cursor.getInt(0);
            einkaufsliste.delete(ListenHelper.TABLE_NAME_EINKAUFSLISTE, ListenHelper.COL_NAME_ID+ "= "+id,null );
        }
        positionClick = -1;
    }


    public void supermaketList(View view) {
        //Sprung in zweite Activity
//        String[] projection = {
//                ListenHelper.COL_NAME_ID,
//                ListenHelper.COL_NAME_DING
//        };
//
//        cursor = einkaufsliste.query(ListenHelper.TABLE_NAME_EINKAUFSLISTE,
//                projection, "1=1", null, null, null, null);


        String id2 = "";
        int id = 0;
        if(positionClick != -1) {
            cursor.moveToPosition(positionClick);
            id = cursor.getInt(0);

            //Toast.makeText(ListActivity.this, ""+id, Toast.LENGTH_SHORT).show();

            id2 = cursor.getString(cursor.getColumnIndex(COL_NAME_ID)); // id is column name in db
            //cursor.getString(cursor.getColumnIndex("COL_NAME_DING"));
            //Toast.makeText(this, projection[0]+projection[1], Toast.LENGTH_LONG).show();
            Toast.makeText(this, id2, Toast.LENGTH_SHORT).show();
        }
        positionClick = -1;
        Intent intent = new Intent();
        intent.putExtra("My_Key", id2);
        intent.setClass(this, SupermarktAuswahl.class);
        startActivity(intent);



    }

}
