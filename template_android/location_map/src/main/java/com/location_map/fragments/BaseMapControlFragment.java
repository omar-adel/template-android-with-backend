package com.location_map.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.location_map.R;
import com.location_map.listeners.GetLocationListeners;
import com.location_map.model.CustomAddress;
import com.location_map.utils.GetLocationHelper;

import static android.content.Context.LOCATION_SERVICE;

public class BaseMapControlFragment<M> extends BaseMapFragment
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener
        , LocationListener , GetLocationListeners {

    public LatLng myLocation;
    public Marker myMarker ;
    private boolean gotLocation;
    boolean checkedGpsBefore = true;
    GetLocationHelper getLocationHelper;
    public static int ENABLE_GPS_INTENT_REQUEST_CODE=349;
    public static int ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE=47;

    @Override
    public void prepareMapClicks() {

        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                CustomAddress customAddress = GetLocationHelper.getAddress(getContainerActivity(),latLng);
                Toast.makeText(getContainerActivity(), customAddress.getCityName(), Toast.LENGTH_LONG).show();
            }
        });

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                infoWindowOnClick(marker);
            }
        });


        doWork();
    }

    @Override
    public void doWork() {

        getLocationHelper = new GetLocationHelper(getContainerActivity(), this);
        accessLocationFunc(true);
    }


    public void accessLocationFunc(boolean check ) {

        if(check)
        {
            if (checkedGpsBefore) {
                if (checkGpsState()) {
                    accessLocation();
                }
            }
        }
        else
        {
            if (checkGpsState()) {
                accessLocation();
            }
        }

    }


    public boolean checkGpsState() {
        boolean availableGps = true;

        ConnectivityManager cm = (ConnectivityManager) getContainerActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            LocationManager locationManager = (LocationManager) getContainerActivity().getSystemService(LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                availableGps = false;

                Snackbar.make(getContainerActivity().findViewById(android.R.id.content), getString(R.string.gpsIsDisabled), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.enable_gps), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent,ENABLE_GPS_INTENT_REQUEST_CODE);
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
        } else {
            availableGps = false;

            Snackbar.make(getContainerActivity().findViewById(android.R.id.content),
                    getString(R.string.noInternetConnection), Snackbar.LENGTH_LONG).show();
        }

        checkedGpsBefore = false;

        return availableGps;
    }


    public void accessLocation() {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 23) {

            if (ContextCompat.checkSelfPermission(getContainerActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                //  return;


                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE);
            }                        // TODO: Consider calling
            else {
                initMyLocationFunction();
            }
        } else {
            initMyLocationFunction();
        }

    }


    public void initMyLocationFunction() {

        try {

            mGoogleMap.setMyLocationEnabled(true);

        } catch (Exception e) {

            Log.d("Exception ", e.toString());

        }

        getLocationHelper.setFusedLocationClient(LocationServices.getFusedLocationProviderClient(getContainerActivity()));

        getContainerActivity().getWindow().addFlags(128);
        getLocationHelper.startLocationClient();

    }




    @Override
    public void onLocationChanged(Location newLocation) {

        if (newLocation != null) {
            gotLocationChangedBase(newLocation);
            if (!gotLocation) {
            gotLocation = true;
            LatLng latLng = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());
            if (mGoogleMap != null)

            {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            }
              }
            gotLocationChanged(newLocation);
        }
    }


    //place marker at current position
    public void gotLocationChangedBase(Location newLocation) {
        LatLng latLng = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());
        myLocation = latLng;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        CustomAddress customAddress = GetLocationHelper.getAddress(getContainerActivity(),latLng);
        markerOptions.title(customAddress.getCity());
        if(myMarker!=null)
        {
            myMarker.remove();
        }
        myMarker = mGoogleMap.addMarker(markerOptions);

    }

    @Override
    public void onPause() {
        super.onPause();
        getLocationHelper.stopLocationUpdates();
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    ) {
                initMyLocationFunction();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ENABLE_GPS_INTENT_REQUEST_CODE)
        {
            accessLocationFunc(false);

        }
    }
}
