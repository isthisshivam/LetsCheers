package com.shivam.world.mad.letscheers;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class current_location extends FragmentActivity implements OnMapReadyCallback
        ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

        private GoogleMap mMap;

        private static final int MY_PERMISSION_REQUEST_CODE = 11;
        private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 10;
        private Location mLastLocation;

        double latitude, longtitude;
        private static int UPDTE_INTERVAL = 5000;
        private static int FASTEST_INTERVAL = 3000;
        private static int DISPLACEMENT = 10;

        Marker myCurrent;
        private GoogleApiClient mGoogleApiClient;
        private LocationRequest mLocationRequest;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_current_location );
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById( R.id.map );
            mapFragment.getMapAsync( this );

            setUpLocation();
        }
//press ctrl+o


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        if (checkPlayServices()) {
                            buildGoogleApiClient();
                            createLocationRequest();
                            displayLocation();
                        }
                    }
                    break;
            }


        }


        private void setUpLocation() {
            if (android.support.v4.app.ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    && android.support.v4.app.ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                requestRuntimePermission();  //call methods
            }
            else {
                if (checkPlayServices()) {
                    buildGoogleApiClient();
                    createLocationRequest();
                    displayLocation();
                }
            }
        }

        //impllements method

        private void displayLocation() {
            if (android.support.v4.app.ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    && android.support.v4.app.ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED)

            {

                return;

            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );
            if (mLastLocation!=null)
            {
                latitude=mLastLocation.getLatitude();
                longtitude=mLastLocation.getLongitude();

                if (myCurrent!=null)
                {
                    myCurrent.remove();//remove old marker
                }
                myCurrent=mMap.addMarker(new MarkerOptions()
                        .position(new LatLng( latitude,longtitude ))
                        .title("You"));
                //move camera to the position
                mMap.animateCamera( CameraUpdateFactory.newLatLngZoom( new LatLng( latitude,longtitude ), 12.0f) );
            }

        }



        private void createLocationRequest()
        {
            mLocationRequest=new LocationRequest();
            mLocationRequest.setInterval( UPDTE_INTERVAL );
            mLocationRequest.setFastestInterval( FASTEST_INTERVAL );
            mLocationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
            mLocationRequest.setSmallestDisplacement( DISPLACEMENT );
        }

        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API ).build();
            mGoogleApiClient.connect();
        }






        private boolean checkPlayServices()
        {
            int resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable( this );
            if (resultCode!= ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError( resultCode )) {
                    GooglePlayServicesUtil.getErrorDialog( resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST ).show();
                } else
                {
                    Toast.makeText( this, "This device can't support", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return false;

            }
            return true;
        }


        private void requestRuntimePermission() {
            android.support.v4.app.ActivityCompat.requestPermissions( this, new String[]
                    {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, MY_PERMISSION_REQUEST_CODE );
        }
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation= location;
            displayLocation();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            displayLocation();
            setLocationUpdates();
        }

        @Override
        public void onConnectionSuspended(int i) {
            mGoogleApiClient.connect();
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }






        private void setLocationUpdates()
        {
            if (android.support.v4.app.ActivityCompat.checkSelfPermission( this,  android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    &&android.support.v4.app.ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            // LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient,mLastLocation,this );

        }


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            // LatLng sydney = new LatLng(latitude , longtitude );
            //mMap.addMarker( new MarkerOptions().position( sydney ).title( "Marker in Here" ) );
            //mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
        }

}