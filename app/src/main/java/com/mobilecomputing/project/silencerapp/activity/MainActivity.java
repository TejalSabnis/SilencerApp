package com.mobilecomputing.project.silencerapp.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mobilecomputing.project.silencerapp.R;
import com.mobilecomputing.project.silencerapp.dto.DataTransfer;
import com.mobilecomputing.project.silencerapp.service.StartupService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DataTransfer dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate started");

        testDb();

        if(!isServiceRunning(StartupService.class)) {
            Intent startServiceIntent = new Intent(this, StartupService.class);
            startService(startServiceIntent);
        }

        /*StartupService startupService = new StartupService(MainActivity.this);
        Location location = startupService.getLocation();
        if (location != null) {
            Log.d(TAG, "latitude: "+location.getLatitude());
            Log.d(TAG, "longitude: "+location.getLongitude());
        } else {
            Log.d(TAG, "location is null!!!");
        }*/

        //Intent mapsActivityIntent = new Intent(this, MapsActivity.class);
        //startActivity(mapsActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void testDb()
    {
        dbHelper = new DataTransfer(this);

        /*UserLocation userLocation1 = new UserLocation();
        userLocation1.setDayOfWeek("Thursday");
        userLocation1.setStartTime(new Date(System.currentTimeMillis()));
        userLocation1.setEndTime(new Date(System.currentTimeMillis()));
        Location loc1 = new Location("provider");
        loc1.setLatitude(50.00);
        loc1.setLongitude(60.00);
        userLocation1.setLoc(loc1);

        UserLocation userLocation = new UserLocation();
        userLocation.setDayOfWeek("Wednesday");
        userLocation.setStartTime(new Date(System.currentTimeMillis()));
        userLocation.setEndTime(new Date(System.currentTimeMillis()));
        Location loc = new Location("provider");
        loc.setLatitude(30.00);
        loc.setLongitude(40.00);
        userLocation.setLoc(loc);

        dbHelper.putInformation(userLocation1);
        dbHelper.putInformation(userLocation);
        dbHelper.getInformation();*/

        dbHelper.dropTable();
        dbHelper.createTable();
        dbHelper.dropCurrentLocationTable();
        dbHelper.createCurrentLocationTable();
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
