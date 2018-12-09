package com.example.template.ui.maps;

import com.example.template.R;
import com.example.template.ui.maps.presenter.IMapDisplayActivityContract;
import com.example.template.ui.maps.presenter.MapDisplayActivityPresenter;

import modules.general.ui.parentview.ParentActivity;

/**
 * Created by Net22 on 11/13/2017.
 */

public class MapDisplayActivity extends ParentActivity<MapDisplayActivityPresenter> implements

        IMapDisplayActivityContract.IMapDisplayActivityContractView {

    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();
        getCsTitle().updateTitle(getString(R.string.map_static));
        getMapDisplayActivityPresenter().openFragmentMap();
    }

    @Override
    public MapDisplayActivityPresenter injectDependencies() {
        return new MapDisplayActivityPresenter(this, this);
    }


    public MapDisplayActivityPresenter getMapDisplayActivityPresenter() {
        return ((MapDisplayActivityPresenter) this.getPresenter());
    }

}
 
 
