package com.mobilecomputing.project.silencerapp.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.location.places.Place;
import com.mobilecomputing.project.silencerapp.dto.DataTransfer;
import com.mobilecomputing.project.silencerapp.model.UserLocation;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tejal on 2015-11-17.
 */
public class TagLocation extends Service {

    private Place currentPlace = null;
    private Date currentStartTime;
    private DataTransfer dbHelper;

    public void tagLocation(Place place) {

        if (currentPlace == null) {
            if (isUniversity(place)) {
                currentPlace = place;
                currentStartTime = new Date(System.currentTimeMillis());
            }
        } else {
            if (!currentPlace.getId().equals(place.getId())) {
                UserLocation userLocation = mapPlaceToLocation(currentPlace);
                dbHelper = new DataTransfer(this);
                dbHelper.putInformation(userLocation);
                if (isUniversity(place)) {
                    currentPlace = place;
                    currentStartTime = new Date(System.currentTimeMillis());
                } else {
                    currentPlace = null;
                }
            }
        }

    }

    public boolean isUniversity(Place place) {
        return place.getPlaceTypes().contains(Place.TYPE_UNIVERSITY);
    }

    public UserLocation mapPlaceToLocation (Place place) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentStartTime);
        int dayInt = cal.get(Calendar.DAY_OF_WEEK);

        Location location = new Location("GooglePlacesAPI");
        location.setLatitude(place.getLatLng().latitude);
        location.setLongitude(place.getLatLng().longitude);

        UserLocation userLocation = new UserLocation();
        userLocation.setDayOfWeek(UserLocation.daysOfWeek.get(dayInt));
        userLocation.setStartTime(currentStartTime);
        userLocation.setEndTime(new Date(System.currentTimeMillis()));
        userLocation.setLoc(location);
        return userLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
