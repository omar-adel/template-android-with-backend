package com.location_map.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.location_map.R;
import com.location_map.model.CustomAddress;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.location_map.listeners.GetLocationListeners;

import static android.content.Context.LOCATION_SERVICE;

public class GetLocationHelper {

    private Context context ;

    public FusedLocationProviderClient getFusedLocationClient() {
        return mFusedLocationClient;
    }

    public void setFusedLocationClient(FusedLocationProviderClient mFusedLocationClient) {
        this.mFusedLocationClient = mFusedLocationClient;
    }

    private FusedLocationProviderClient mFusedLocationClient;


    public LocationRequest locationRequest;
    private LocationCallback mLocationCallback;
   private GetLocationListeners getLocationListeners ;
    public GetLocationHelper(Context context, GetLocationListeners getLocationListeners)
    {
          this.context=context;
        this.getLocationListeners=getLocationListeners;
    }

    public void getMyLocation() {

        accessLocationFunc();
    }

    private void accessLocationFunc( ) {
        if (checkGpsState(context)) {
            accessLocation();
        }

    }

    private void accessLocation( ) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 23) {

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                //  return;


            }
            else {
                initMyLocationFunction();
            }
        } else {
            initMyLocationFunction();
        }

    }

    private void initMyLocationFunction() {


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        startLocationClient();

    }

    public void startLocationClient() {

        try {
            if (locationRequest == null) {
                locationRequest = LocationRequest.create();
                prepareLocationRequest();
                prepareLocationCallBack();
                requestLocationUpdates();
            } else {
                requestLocationUpdates();
            }

        } catch (Exception e) {
            Log.d("exception startLoc ", e.toString());
        }

    }


    private void prepareLocationRequest() {

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(1 * 1000);
        locationRequest.setInterval(10 * 1000);
       //locationRequest.setSmallestDisplacement(0);

    }

    private void prepareLocationCallBack() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                super.onLocationResult(result);
                //mCurrentLocation = locationResult.getLastLocation();
                for (Location location : result.getLocations()) {
                    // Update UI with location data
                    onLocationChanged(location);
                }

                /**
                 * 지속적으로 위치정보를 받으려면
                 * mLocationRequest.setNumUpdates(1) 주석처리하고
                 * 밑에 코드를 주석을 푼다
                 */
                //mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }

            //Location관련정보를 모두 사용할 수 있음을 의미
            @Override
            public void onLocationAvailability(LocationAvailability availability) {
                //boolean isLocation = availability.isLocationAvailable();
            }
        };

    }

    private void requestLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
    }

    public void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void onLocationChanged(Location newLocation) {
        if (newLocation != null) {

            getLocationListeners.onLocationChanged(newLocation);

        }
    }


    public static  CustomAddress getAddress(Context context , LatLng latLng) {
        CustomAddress customAddress =new CustomAddress();
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latLng.latitude,
                    latLng.longitude, 1);
            customAddress.setCityName(addresses.get(0).getAddressLine(0));
            customAddress.setStateName(addresses.get(0).getAddressLine(1));
            customAddress.setCountryName(addresses.get(0).getAddressLine(2));

            customAddress.setAddress(addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            customAddress.setCity(addresses.get(0).getLocality());
            customAddress.setState(addresses.get(0).getAdminArea());
            customAddress.setCountry(addresses.get(0).getCountryName());
            customAddress.setPostalCode(addresses.get(0).getPostalCode());
            customAddress.setKnownName(addresses.get(0).getFeatureName()); // Only if available else return NULL


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException ofp) {
            ofp.printStackTrace();
        }


        return customAddress ;
    }
    public static boolean checkGpsState(Context  context) {
        boolean availableGps = true;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                availableGps = false;

                Toast.makeText(context, context.getString(R.string.gpsIsDisabled), Toast.LENGTH_LONG).show();
            }
        } else {
            availableGps = false;

            Toast.makeText(context, context.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
        }


        return availableGps;
    }

}
