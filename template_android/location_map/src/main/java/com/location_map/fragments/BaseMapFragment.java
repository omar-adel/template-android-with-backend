package com.location_map.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.location_map.R;
import com.location_map.listeners.MapListeners;
import com.location_map.model.CustomAddress;
import com.location_map.utils.GetLocationHelper;
import com.mvp_base.Base;
import com.mvp_base.BaseSupportFragment;

/**
 * Created by omar on 24/05/2017.
 */

public class BaseMapFragment<P extends Base.IPresenter> extends BaseSupportFragment
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        MapListeners {

    ///// map display
    public GoogleMap mGoogleMap;
    public SupportMapFragment mapFragment;

    @Override
    public void onResume() {
        super.onResume();
        onResumeBase();
    }

    public void onResumeBase() {
        prepareMapFragFunc();
        mapFragment.getMapAsync(this);
    }


    public int getMapId()
    {
        return 0 ;
    }
    public void prepareMapFragFunc() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(getMapId());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        prepareMap();
    }

    public void prepareMap() {
        mGoogleMap.clear();
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        prepareMapClicks();
    }

    @Override
    public void prepareMapClicks() {

    }

    @Override
    public void gotLocationChanged(Location newLocation) {

    }

    @Override
    public void doWork() {

    }



    @Override
    public int getLayoutResource() {
        return 0;
    }

    @Override
    public void configureUI() {

    }

    @Override
    public Base.IPresenter injectDependencies() {
        return null;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        CustomAddress customAddress = GetLocationHelper.getAddress(getContainerActivity(),marker.getPosition());
        Toast.makeText(getContainerActivity()," "+getString(R.string.marker_clicked)+" "
                +customAddress.getCityName(),Toast.LENGTH_LONG).show();

        return false;
    }


     public void infoWindowOnClick(Marker marker) {
        CustomAddress customAddress = GetLocationHelper.getAddress(getContainerActivity(),marker.getPosition());
        Toast.makeText(getContainerActivity(),
                " "+getString(R.string.info_window_clicked)+" "+customAddress.getCityName(),Toast.LENGTH_LONG).show();
    }





}
