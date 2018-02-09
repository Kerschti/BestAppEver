package com.example.kerstindittmann.bestappever;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * ListenHelper erstellt die Datenbank und die zugehörigen Methoden.
 * Erstellt von: Tanja Foertsch am 01.02.2018
 */

public class ListenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME_EINKAUFSLISTE = "liste";
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_DING = "ding";

    private Context context;
    private static final int VERSION = 2;
    private static ListenHelper instance;

    private final String SQL_COMMAND = "CREATE TABLE " + TABLE_NAME_EINKAUFSLISTE + " (" +
            COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_NAME_DING + " VARCHAR(40) NOT NULL" +
            " )";

    //Version wird hier definiert
    public static ListenHelper createInstance(Context context, String databaseName ){

        if(instance == null){
            instance = new ListenHelper(context, databaseName);
        }
        return instance;
    }

    private ListenHelper(Context context, String name){
        super(context, name, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(SQL_COMMAND);
        }catch(SQLException e){
            Log.e("SQLiteOpenHelper", "Fehler" + e.getMessage());
        }
    }

    //bekommt Datenbank uebergeben, alte und neue Version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion == 1 && newVersion == 2){
            try{
                db.execSQL(SQL_COMMAND);
                Toast.makeText(context, "Tabelle erzeugt", Toast.LENGTH_SHORT).show();
            }catch(SQLException e){
                Log.e("SQLiteOpenHelper", "Fehler" + e.getMessage());
            }
        }
    }
}
