package com.example.kerstindittmann.bestappever;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SupermarktAuswahl extends AppCompatActivity {

    ListView supermarketlist;
    Cursor cursor;
    private int positionClick = -1;

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

        });}
    }
}
