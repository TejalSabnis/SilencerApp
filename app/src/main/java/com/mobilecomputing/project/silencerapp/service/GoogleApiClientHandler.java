package com.mobilecomputing.project.silencerapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.mobilecomputing.project.silencerapp.dto.DataTransfer;
import com.mobilecomputing.project.silencerapp.model.MyPlace;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GoogleApiClientHandler extends Service
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public GoogleApiClientHandler() {

    }

    private static final String TAG = "GoogleApiClientHandler";

    private TagLocation tagLocation;
    private MyTimerTask myTimerTask;
    private DataTransfer dbHelper;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {

        super.onCreate();
        try {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            dbHelper = new DataTransfer(this);
            tagLocation = new TagLocation(dbHelper);
            myTimerTask = new MyTimerTask();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCurrentPlace() {
        Log.d(TAG, "getCurrentPlace started at: "+ new Date(System.currentTimeMillis()));
        try {
            Place place = new MyPlace();
            tagLocation.tagLocation(place);
            if (MyPlace.counter >= 5) {
                Log.d(TAG, "getCurrentPlace cancelling timer task");
                myTimerTask.timer.cancel();
                tagLocation.getInformation();
            }
            /*PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                    .getCurrentPlace(mGoogleApiClient, null);
            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                    List<MyPlaceLikelihood> myPlaceLikelihoodList = new ArrayList<MyPlaceLikelihood>();
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        MyPlaceLikelihood myPlaceLikelihood = new MyPlaceLikelihood();
                        myPlaceLikelihood.setPlaceLikelihood(placeLikelihood);
                        myPlaceLikelihoodList.add(myPlaceLikelihood);
                        Log.d(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));
                    }
                    Collections.sort(myPlaceLikelihoodList);
                    tagLocation.tagLocation(myPlaceLikelihoodList.get(myPlaceLikelihoodList.size()-1).getPlaceLikelihood().getPlace());
                    likelyPlaces.release();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    class MyTimerTask extends TimerTask {

        public MyTimerTask() {
            timer = new Timer();
            timer.schedule(this, 0, 10*1000); //30*60*1000
        }

        private Timer timer;

        @Override
        public void run() {
            getCurrentPlace();
        }
    }
}
