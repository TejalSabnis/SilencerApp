package com.mobilecomputing.project.silencerapp.model;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tejal on 2015-11-19.
 */
public class MyPlace implements Place {
    public MyPlace() {
        counter++;
    }

    public static int counter = 0;

    @Override
    public String getId() {
        switch (counter) {
            case 1: {
                return "id1";
            }
            case 2: {
                return "id2";
            }
            case 3: {
                return "id1";
            }
            case 4: {
                return "id2";
            }
            case 5: {
                return "id3";
            }
            default: {
                return "default";
            }
        }
    }

    @Override
    public List<Integer> getPlaceTypes() {
        List<Integer> placeTypes =  new ArrayList<Integer>();
        switch (counter) {
            case 1: {
                placeTypes.add(Place.TYPE_UNIVERSITY);
                return placeTypes;
            }
            case 2: {
                placeTypes.add(Place.TYPE_UNIVERSITY);
                return placeTypes;
            }
            case 3: {
                placeTypes.add(Place.TYPE_UNIVERSITY);
                return placeTypes;
            }
            case 4: {
                placeTypes.add(Place.TYPE_UNIVERSITY);
                return placeTypes;
            }
            case 5: {
                placeTypes.add(Place.TYPE_ACCOUNTING);
                return placeTypes;
            }
            default: {
                return placeTypes;
            }
        }
    }

    @Override
    public CharSequence getAddress() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public CharSequence getName() {
        switch (counter) {
            case 1: {
                return "name1";
            }
            case 2: {
                return "name2";
            }
            case 3: {
                return "name1";
            }
            case 4: {
                return "name2";
            }
            case 5: {
                return "name3";
            }
            default: {
                return "default";
            }
        }
    }

    @Override
    public LatLng getLatLng() {
        switch (counter) {
            case 1: {
                return new LatLng(1,1);
            }
            case 2: {
                return new LatLng(2,2);
            }
            case 3: {
                return new LatLng(1,1);
            }
            case 4: {
                return new LatLng(2,2);
            }
            case 5: {
                return new LatLng(3,3);
            }
            default: {
                return new LatLng(6,6);
            }
        }
    }

    @Override
    public LatLngBounds getViewport() {
        return null;
    }

    @Override
    public Uri getWebsiteUri() {
        return null;
    }

    @Override
    public CharSequence getPhoneNumber() {
        return null;
    }

    @Override
    public float getRating() {
        return 0;
    }

    @Override
    public int getPriceLevel() {
        return 0;
    }

    @Override
    public Place freeze() {
        return null;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }
}
