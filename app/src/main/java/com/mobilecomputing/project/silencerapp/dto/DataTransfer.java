package com.mobilecomputing.project.silencerapp.dto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobilecomputing.project.silencerapp.model.UserLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejal on 2015-10-22.
 */
public class DataTransfer extends SQLiteOpenHelper{

    private static final String TAG = "DataTransfer";

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "user_locations_database";

    public static final String USER_LOCATIONS = "user_locations";

    public static final String REC_ID = "rec_id";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String DAY = "day";

    public DataTransfer(Context cxt) {
        super(cxt, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table user_locations (" +
                    " rec_id integer PRIMARY KEY autoincrement," +
                    " latitude real," +
                    " longitude real," +
                    " start_time datetime," +
                    " end_time datetime," +
                    " day text )");

            Log.d(TAG, "onCreate table created successfully");
        }
        catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table user_locations (" +
                    " rec_id integer PRIMARY KEY autoincrement," +
                    " latitude real," +
                    " longitude real," +
                    " start_time datetime," +
                    " end_time datetime," +
                    " day text )");

            Log.d(TAG, "createTable table created successfully");
        }
        catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void putInformation(UserLocation userLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LATITUDE, userLocation.getLoc().getLatitude());
        cv.put(LONGITUDE, userLocation.getLoc().getLongitude());
        cv.put(START_TIME, userLocation.getStartTime().toString());
        cv.put(END_TIME, userLocation.getEndTime().toString());
        cv.put(DAY, userLocation.getDayOfWeek());
        db.insert(USER_LOCATIONS, null, cv);
        Log.d(TAG, "putInformation() returned " + "successful insert");
    }

    public List<UserLocation> getInformation()
    {
        List<UserLocation> userLocs = new ArrayList<UserLocation>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from user_locations", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            Log.d("DataTransfer", res.getString(res.getColumnIndex(LATITUDE)));
            Log.d("DataTransfer", res.getString(res.getColumnIndex(LONGITUDE)));
            Log.d("DataTransfer", res.getString(res.getColumnIndex(START_TIME)));
            Log.d("DataTransfer", res.getString(res.getColumnIndex(END_TIME)));
            Log.d("DataTransfer", res.getString(res.getColumnIndex(DAY)));
            Log.d("DataTransfer", res.getString(res.getColumnIndex(REC_ID)));
            res.moveToNext();
        }

        return userLocs;
    }

    public List<UserLocation> mapData(Cursor res)
    {
        List<UserLocation> userLocs = new ArrayList<UserLocation>();

        res.moveToFirst();
        while(res.isAfterLast() == false){
            UserLocation userLocation = new UserLocation();
            userLocation.setDayOfWeek(res.getString(res.getColumnIndex(DAY)));
            //userLocation.setStartTime();
            //userLocation.setEndTime();
            //userLocation.setLoc(new Location(res.getDouble(res.getColumnIndex(LATITUDE)), res.getDouble(res.getColumnIndex(LONGITUDE))));
            res.moveToNext();
        }

        return userLocs;
    }
}
