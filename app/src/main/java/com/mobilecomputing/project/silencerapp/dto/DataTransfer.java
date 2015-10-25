package com.mobilecomputing.project.silencerapp.dto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tejal on 2015-10-22.
 */
public class DataTransfer extends SQLiteOpenHelper{

    public static final int database_version = 1;

    public static final String DATABASE_NAME = "user_locations_database";

    DataTransfer(Context cxt) {
        super(cxt, DATABASE_NAME, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table user_locations (" +
                    " rec_id integer PRIMARY KEY autoincrement," +
                    " latitude real," +
                    " longitude real," +
                    " start_time text," +
                    " end_time text," +
                    " day text");
        }
        catch(Exception ex){
            Log.e("Database", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
