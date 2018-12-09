package com.location_map.fragments;

import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.location_map.model.CustomAddress;
import com.location_map.utils.GetLocationHelper;

/**
 * Created by omar on 24/05/2017.
 */

public class BaseMapDisplayFragment<M> extends BaseMapFragment {

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


}
