package dali.survillance.service;


import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import dali.survillance.activity.takescreenshot;
import dali.survillance.controlleur.GpsControlleur;
import dali.survillance.controlleur.JobsControlleur;
import dali.survillance.model.GPS;
import dali.survillance.model.Jobs;
import dali.survillance.model.Tracker;
import dali.survillance.other.Constant;
import dali.survillance.other.SessionIdentifierGenerator;
import dali.survillance.other.SessionManager;

/**
 * Created by Mohamed ali on 24/01/2017.
 */

public class jobService extends IntentService implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String Latitude,Longitude;
    private Location mCurrentLocation;


    public jobService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Create an instance of GoogleAPIClient.

        Tracker t=new Tracker();
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String id_tracker = sessionManager.getUserTracker().get(sessionManager.KEY_TRACK);
        t.setId(id_tracker);
        while (Constant.checkNetworkConnection(getApplicationContext())) {
            Log.i("enter","enter");
            ArrayList<Jobs> list = JobsControlleur.getJobs(id_tracker);
            Log.i("taille","t:"+list.size());
            for (int i = 0; i < list.size(); i++) {
                Jobs j = list.get(i);
                if (j.getJobs().equals("GPS")) {

                    GPS gps=new GPS();
                    gps.setLat(Latitude);
                    gps.setLon(Longitude);
                    gps.setTracker(t);
                    GpsControlleur.sendPosition(gps);
                    
                } else if (j.getJobs().equals("DO")) {
                   Log.i("do","do");

                   String name= SessionIdentifierGenerator.Name();
                    Intent take=new Intent(this,takescreenshot.class);
                    take.putExtra("name",name);
                    take.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(take);
                }
                JobsControlleur.finishJobs(j.getId());
                list.remove(i);
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }





    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        super.onDestroy();
        Log.i("stop intent service", "stop");
        stopSelf();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
           Latitude =String.valueOf(mLastLocation.getLatitude());
           Longitude=String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Latitude =String.valueOf(mCurrentLocation.getLatitude());
        Longitude=String.valueOf(mCurrentLocation.getLongitude());
    }
}
