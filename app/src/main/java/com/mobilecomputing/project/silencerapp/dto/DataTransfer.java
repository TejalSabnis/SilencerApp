package com.mobilecomputing.project.silencerapp.dto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobilecomputing.project.silencerapp.model.PlaceBean;
import com.mobilecomputing.project.silencerapp.model.UserLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tejal on 2015-10-22.
 */
public class DataTransfer extends SQLiteOpenHelper{

    private static final String TAG = "DataTransfer";

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "user_locations_database";

    public static final String USER_LOCATIONS = "user_locations";
    public static final String CURRENT_LOCATION = "current_location";

    public static final String REC_ID = "rec_id";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String DAY = "day";
    public static final String PLACE_NAME = "place_name";
    public static final String PLACE_ID = "place_id";

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
                    " start_time integer," +
                    " end_time integer," +
                    " day text," +
                    " place_name text )");

            Log.d(TAG, "createTable user_locations created successfully");
        }
        catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void dropTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("drop table user_locations");

            Log.d(TAG, "createTable user_locations dropped successfully");
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
        cv.put(START_TIME, userLocation.getStartTime());
        cv.put(END_TIME, userLocation.getEndTime());
        cv.put(DAY, userLocation.getDayOfWeek());
        cv.put(PLACE_NAME, userLocation.getPlaceName());
        long k = db.insert(USER_LOCATIONS, null, cv);
        Log.d(TAG, "putInformation() returned " +k+ "successful insert");
    }

    public List<UserLocation> getInformation()
    {
        Log.d(TAG, "getInformation started");
        List<UserLocation> userLocs = new ArrayList<UserLocation>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from user_locations", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            Log.d(TAG, "LATITUDE: "+ res.getString(res.getColumnIndex(LATITUDE)));
            Log.d(TAG, "LONGITUDE: "+ res.getString(res.getColumnIndex(LONGITUDE)));
            Log.d(TAG, "START_TIME: "+ new Date(res.getLong(res.getColumnIndex(START_TIME))));
            Log.d(TAG, "END_TIME: "+ new Date(res.getLong(res.getColumnIndex(END_TIME))));
            Log.d(TAG, "DAY: "+ res.getString(res.getColumnIndex(DAY)));
            Log.d(TAG, "PLACE_NAME: "+ res.getString(res.getColumnIndex(PLACE_NAME)));
            Log.d(TAG, "REC_ID: "+ res.getString(res.getColumnIndex(REC_ID)));
            res.moveToNext();
        }

        return userLocs;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("delete * from user_locations", null);
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

    public void createCurrentLocationTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table current_location (" +
                    " place_id text," +
                    " latitude real," +
                    " longitude real," +
                    " start_time integer," +
                    " place_name text )");

            Log.d(TAG, "createTable current_location created successfully");
        }
        catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void dropCurrentLocationTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("drop table current_location");

            Log.d(TAG, "createTable current_location dropped successfully");
        }
        catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void putCurrentLocation(PlaceBean currentPlace)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LATITUDE, currentPlace.getLatitude());
        cv.put(LONGITUDE, currentPlace.getLongitude());
        cv.put(START_TIME, currentPlace.getStartTime());
        cv.put(PLACE_ID, currentPlace.getPlaceId());
        cv.put(PLACE_NAME, currentPlace.getPlaceName());
        long k = db.insert(CURRENT_LOCATION, null, cv);
        Log.d(TAG, "putCurrentLocation() returned " +k+ "successful insert");
    }

    public PlaceBean getCurrentLocation()
    {
        PlaceBean currentLocation = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from current_location", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            currentLocation = new PlaceBean();
            currentLocation.setPlaceId(res.getString(res.getColumnIndex(PLACE_ID)));
            currentLocation.setPlaceName(res.getString(res.getColumnIndex(PLACE_NAME)));
            currentLocation.setLatitude(res.getDouble(res.getColumnIndex(LATITUDE)));
            currentLocation.setLongitude(res.getDouble(res.getColumnIndex(LONGITUDE)));
            currentLocation.setStartTime(res.getLong(res.getColumnIndex(START_TIME)));

            /*Log.d(TAG, "LATITUDE: "+ currentLocation.getLatitude());
            Log.d(TAG, "LONGITUDE: "+ currentLocation.getLongitude());
            Log.d(TAG, "START_TIME: "+ currentLocation.getStartTime());
            Log.d(TAG, "PLACE_ID: "+ currentLocation.getPlaceId());
            Log.d(TAG, "PLACE_NAME: "+ currentLocation.getPlaceName());*/
            res.moveToNext();
        }
        return currentLocation;
    }

    public void deleteCurrentLocation()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("delete from current_location", null);
        Log.d(TAG, "deleteCurrentLocation() successful delete");
    }
}
