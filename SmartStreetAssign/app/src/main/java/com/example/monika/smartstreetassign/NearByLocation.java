package com.example.monika.smartstreetassign;

/**
 * Created by monika on 9/18/17.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NearByLocation extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;

    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location endLocation;
    private Marker presentLocationMarker;
    public static final int REQ_LOC_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbylocation);

        Button searchButton;
        EditText loc;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            locationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /* Handling of the Permissions required */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQ_LOC_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            gApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission cannot be granted", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else
        {
            gApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    /* Handling of the Search Button */

    public void onSearchClick(View view)
    {
        if(view.getId() == R.id.searchButton)
        {
            EditText loc = (EditText)findViewById(R.id.loc);
            String location = loc.getText().toString();
            List<Address> addressList = null;
            MarkerOptions mk = new MarkerOptions();
            if(!location.equals(""))
            {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i = 0; addressList.size() > i; i++)
                {
                    Address addr = addressList.get(i);
                    LatLng latlng1 = new LatLng(addr.getLatitude(), addr.getLongitude());
                    mk.position(latlng1);
                    mk.title("Here is the place");
                    mMap.addMarker(mk);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng1));

                }
            }
        }
    }


    protected synchronized void gApiClient()
    {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }


    @Override
    public void onLocationChanged(Location location)
    {
        endLocation = location;

        if(presentLocationMarker !=null)
        {
            presentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("This is the current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        presentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    public boolean locationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOC_CODE);
            }
            return false;
        }
        else
        {
            return true;
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

