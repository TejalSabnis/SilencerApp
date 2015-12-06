package com.mobilecomputing.project.silencerapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartupService extends Service {
    public  StartupService() {

    }

    private static final String TAG = "StartupService";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind ");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent GAPIHandlerServiceIntent = new Intent(this, GoogleApiClientHandler.class);
        startService(GAPIHandlerServiceIntent);
        Log.d(TAG, "onCreate ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind ");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind ");
    }
}
