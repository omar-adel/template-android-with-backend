package com.example.template.ui.maps.presenter;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/13/2017.
 */

public interface IMapControlActivityContract {

    public interface IMapControlActivityContractView {
    }

    public interface IMapControlActivityContractPresenter extends Base.IPresenter {
          void  openFragmentMap();
    }
}
