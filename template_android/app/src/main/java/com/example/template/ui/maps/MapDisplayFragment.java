package com.example.template.ui.maps;

import com.example.template.R;
import com.example.template.model.bean.CustomAddress;
import com.example.template.ui.maps.presenter.IMapDisplayFragmentContract;
import com.example.template.ui.maps.presenter.MapDisplayFragmentPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.location_map.fragments.BaseMapDisplayFragment;

import static modules.general.utils.GetLocationHelper.getAddress;

/**
 * Created by Net22 on 11/13/2017.
 */

public class MapDisplayFragment extends BaseMapDisplayFragment<MapDisplayFragmentPresenter> implements
        IMapDisplayFragmentContract.IMapDisplayFragmentContractView {
    @Override
    public int getLayoutResource() {
        return R.layout.frg_map_display;
    }

    @Override
    public int getMapId()
    {
        return R.id.map ;
    }


    @Override
    public void configureUI() {

    }

    @Override
    public MapDisplayFragmentPresenter injectDependencies() {
        return new MapDisplayFragmentPresenter(getContainerActivity(), this);
    }

    @Override
    public void doWork() {

        float latitude = 30.111f;
        float longitude = 31.097f;

        LatLng latLng = new LatLng(latitude, longitude);
        if (mGoogleMap != null)

        {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            CustomAddress customAddress = getAddress(getContainerActivity(),latLng);
            markerOptions.title(customAddress.getCity());
            mGoogleMap.addMarker(markerOptions);
        }

    }

}


