package com.example.kerstindittmann.bestappever;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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


public class ListActivity extends AppCompatActivity {

    private SQLiteDatabase einkaufsliste;
    TextView dbListe;
    CheckBox check;
    private int positionClick = -1;
    private Cursor cursor;
    private Context context;
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView dbListe = (ListView) findViewById(R.id.db_liste);
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
            }

        });}

    public void onZuerickClick(View view){
        finish();
    }

    public void loeschen(View view){
        Log.i("POSITION THING", ""+positionClick);
        if(positionClick != -1){
            Log.i("POSITION THING", ""+positionClick);
            cursor.moveToPosition(positionClick);
            int id = cursor.getInt(0);
            einkaufsliste.delete(ListenHelper.TABLE_NAME_EINKAUFSLISTE, ListenHelper.COL_NAME_ID+ "= "+id,null );
        }
        positionClick = -1;
    }









}