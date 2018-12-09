package com.example.template.ui.maps;

import android.location.Location;
import android.util.Log;

import com.example.template.R;
import com.example.template.ui.maps.presenter.IMapControlFragmentContract;
import com.example.template.ui.maps.presenter.MapControlFragmentPresenter;
import com.location_map.fragments.BaseMapControlFragment;

/**
 * Created by Net22 on 11/13/2017.
 */

public class MapControlFragment extends BaseMapControlFragment<MapControlFragmentPresenter> implements

        IMapControlFragmentContract.IMapControlFragmentContractView {
    @Override
    public int getLayoutResource() {
        return R.layout.frg_map_control;
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
    public void gotLocationChanged(Location newLocation) {

        Log.e("gotLocationChanged Longitude:", "" + String.valueOf(newLocation.getLongitude()));
        Log.e("gotLocationChanged Latitude:", String.valueOf(newLocation.getLatitude()));

    }


    @Override
    public MapControlFragmentPresenter injectDependencies() {
        return new MapControlFragmentPresenter(getContainerActivity(), this);
    }
}
