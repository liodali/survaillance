package dali.survillance.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dali.survillance.R;
import dali.survillance.model.GPS;

public class MoreGPSTrack extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener, OnMapReadyCallback {


    private TextView name,distance;
    private Button seeMore,cancel;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation,TrackLocation;
    private String Latitude,Longitude;
    private Location mCurrentLocation;
    GPS gps;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_gpstrack);
        gps=getIntent().getParcelableExtra("gps");

        name=(TextView)findViewById(R.id.id_notif_act_name_gps);
        distance=(TextView)findViewById(R.id.id_notif_act_distance);

        seeMore=(Button)findViewById(R.id.id_notif_act_bt_seemore);
        cancel=(Button)findViewById(R.id.id_notif_act_cancel);
        Log.i("nom",gps.getTracker().getNom());
        name.setText(gps.getTracker().getNom()+" "+gps.getTracker().getPrenom());
        TrackLocation=new Location("");
        TrackLocation.setLatitude(Double.valueOf(gps.getLat()));
        TrackLocation.setLongitude(Double.valueOf(gps.getLon()));

        if (googleServiceAvaible()){
            initMap();
        }else{

        }



        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMap() {

        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.Mapfragment);
        mapFragment.getMapAsync(this);


    }

    public boolean googleServiceAvaible(){
        GoogleApiAvailability apiAvailability= GoogleApiAvailability.getInstance();
        int is=apiAvailability.isGooglePlayServicesAvailable(this);
        if (is==ConnectionResult.SUCCESS){
            return true;
        }else{
            Toast.makeText(this,"Impossible connect to play service ",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        super.onDestroy();

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
        mLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLocation != null) {
            Latitude =String.valueOf(mLocation.getLatitude());
            Longitude=String.valueOf(mLocation.getLongitude());
            float distanceInMeters = mLocation.distanceTo(TrackLocation)/1000;
            distance.setText("distance is: "+distanceInMeters+" KM");

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;

        mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());
        LatLng ll=new LatLng(TrackLocation.getLatitude(),TrackLocation.getLongitude());
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,10);

        mGoogleMap.moveCamera(update);;
        Marker newmarker = mGoogleMap.addMarker(new MarkerOptions().position(ll)
                .title("postion of "+gps.getTracker().getNom()));
    }

}
