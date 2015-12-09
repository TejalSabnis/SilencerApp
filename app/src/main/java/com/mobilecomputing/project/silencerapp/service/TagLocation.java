package com.mobilecomputing.project.silencerapp.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.mobilecomputing.project.silencerapp.dto.DataTransfer;
import com.mobilecomputing.project.silencerapp.model.PlaceBean;
import com.mobilecomputing.project.silencerapp.model.UserLocation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Tejal on 2015-11-17.
 */
public class TagLocation extends Service {
    public TagLocation(DataTransfer dbHelper) {
        this.dbHelper = dbHelper;
    }

    private static final String TAG = "TagLocation";

    private PlaceBean currentPlace;
    private DataTransfer dbHelper;

    public void tagLocation(Place place) {

        List<UserLocation> userLocs = dbHelper.getInformation();
        currentPlace = dbHelper.getCurrentLocation();
        if (currentPlace == null) {
            if (isUniversity(place)) {
                UserLocation userLocation = isLocExisting(userLocs, place);
                if (userLocation != null) {
                    putSilentOn(userLocation);
                    if (userLocation.getConfidence() < 3) {
                        Log.d(TAG, "tagLocation : updating confidence for"+userLocation.getRecId());
                        dbHelper.updateConfidence(userLocation);
                    }
                }
                else
                    putSilentOn();
                putCurrentLocation(place);
            }
        } else {
            Log.d(TAG, "tagLocation : currentPlace.getId(): "+currentPlace.getPlaceId()+" place.getId(): "+place.getId());
            if (!currentPlace.getPlaceId().equals(place.getId())) {
                if (isLocExisting(userLocs, currentPlace) == null) {
                    UserLocation userLocation = mapPlaceToLocation(currentPlace);
                    dbHelper.putInformation(userLocation);
                }
                if (isUniversity(place)) {
                    dbHelper.deleteCurrentLocation();
                    putCurrentLocation(place);
                    UserLocation userLocation1 = isLocExisting(userLocs, place);
                    if (userLocation1 != null) {
                        putSilentOn(userLocation1);
                        if (userLocation1.getConfidence() < 3) {
                            Log.d(TAG, "tagLocation : updating confidence for"+userLocation1.getRecId());
                            dbHelper.updateConfidence(userLocation1);
                        }
                    }
                    else
                        putSilentOn();
                    putCurrentLocation(place);
                } else {
                    dbHelper.deleteCurrentLocation();
                    putSilentOff();
                }
            }
        }
    }

    public UserLocation isLocExisting(List<UserLocation> userLocs, Place place) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        cal.setTime(date);
        int dayInt = cal.get(Calendar.DAY_OF_WEEK);
        for(UserLocation userLocation : userLocs){
            if (userLocation.getPlaceName().equals(place.getName().toString())) {
                Log.d(TAG, "isLocExisting : names are equal");
                Date locStartDate = new Date(userLocation.getStartTime());
                //Date locEndDate = new Date(userLocation.getEndTime());
                long diff = date.getTime() - locStartDate.getTime();
                if (UserLocation.daysOfWeek.get(dayInt).equals(userLocation.getDayOfWeek())
                        && diff < 30*60*1000) {
                    Log.d(TAG, "isLocExisting : returned: "+userLocation.getRecId());
                    return userLocation;
                }
            }
        }
        Log.d(TAG, "isLocExisting : returned null");
        return null;
    }

    public UserLocation isLocExisting(List<UserLocation> userLocs, PlaceBean place) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        cal.setTime(date);
        int dayInt = cal.get(Calendar.DAY_OF_WEEK);
        for(UserLocation userLocation : userLocs){
            if (userLocation.getPlaceName().equals(place.getPlaceName())) {
                Log.d(TAG, "isLocExisting : names are equal");
                Date locStartDate = new Date(userLocation.getStartTime());
                //Date locEndDate = new Date(userLocation.getEndTime());
                long diff = date.getTime() - locStartDate.getTime();
                if (UserLocation.daysOfWeek.get(dayInt).equals(userLocation.getDayOfWeek())
                        && diff < 30*60*1000) {
                    Log.d(TAG, "isLocExisting : returned: "+userLocation.getRecId());
                    return userLocation;
                }
            }
        }
        Log.d(TAG, "isLocExisting : returned null");
        return null;
    }

    public boolean isUniversity(Place place) {
        Log.d(TAG, "isUniversity returned: "+place.getPlaceTypes().contains(Place.TYPE_UNIVERSITY));
        return place.getPlaceTypes().contains(Place.TYPE_UNIVERSITY);
    }

    public UserLocation mapPlaceToLocation (PlaceBean place) {
        Log.d(TAG, "mapPlaceToLocation started");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(place.getStartTime()));
        int dayInt = cal.get(Calendar.DAY_OF_WEEK);

        Location location = new Location("GooglePlacesAPI");
        location.setLatitude(place.getLatitude());
        location.setLongitude(place.getLongitude());

        UserLocation userLocation = new UserLocation();
        userLocation.setDayOfWeek(UserLocation.daysOfWeek.get(dayInt));
        userLocation.setStartTime(place.getStartTime());
        userLocation.setEndTime(System.currentTimeMillis());
        userLocation.setLoc(location);
        userLocation.setPlaceName(place.getPlaceName());
        userLocation.setConfidence(1);
        return userLocation;
    }

    public void putCurrentLocation(Place place) {
        currentPlace = new PlaceBean();
        currentPlace.setLongitude(place.getLatLng().longitude);
        currentPlace.setLatitude(place.getLatLng().latitude);
        currentPlace.setStartTime(System.currentTimeMillis());
        currentPlace.setPlaceId(place.getId());
        currentPlace.setPlaceName(place.getName().toString());
        dbHelper.putCurrentLocation(currentPlace);
    }

    public void putSilentOn() {
        //Intent userConfIntent = new Intent(this, UserConfirmation.class);
        //startActivity(userConfIntent);
        StartupService.mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public void putSilentOn(UserLocation userLocation) {
        if (userLocation.getConfidence()+1 == 2) {
            //alert user by vibrating and put phone on silent
            StartupService.vibrator.vibrate(1000);
            StartupService.mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if (userLocation.getConfidence()+1 == 3) {
            //put phone on silent directly
            StartupService.mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }

    public void putSilentOff() {
        StartupService.mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public List<UserLocation> getInformation()
    {
        return dbHelper.getInformation();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
