package com.mobilecomputing.project.silencerapp.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mobilecomputing.project.silencerapp.model.UserLocation;

/**
 * Created by Tejal on 2015-12-07.
 */
public class Silencer extends Service {

    public void putSilentOn() {
        // Vibrate for 500 milliseconds
        StartupService.vibrator.vibrate(2000);
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
