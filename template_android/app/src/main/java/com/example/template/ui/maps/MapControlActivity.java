package com.example.template.ui.maps;

import com.example.template.R;
import com.example.template.ui.maps.presenter.IMapControlActivityContract;
import com.example.template.ui.maps.presenter.MapControlActivityPresenter;

import modules.general.ui.parentview.ParentActivity;

public class MapControlActivity extends ParentActivity<MapControlActivityPresenter> implements

        IMapControlActivityContract.IMapControlActivityContractView {


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();
        getCsTitle().updateTitle(getString(R.string.map_dynamic));
        getMapControlActivityPresenter().openFragmentMap();
    }

    @Override
    public MapControlActivityPresenter injectDependencies() {
        return new MapControlActivityPresenter(this, this);
    }

    public MapControlActivityPresenter getMapControlActivityPresenter() {
        return ((MapControlActivityPresenter) this.getPresenter());
    }


}


