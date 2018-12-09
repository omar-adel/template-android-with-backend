package com.example.template.ui.socketTest.presenter;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/13/2017.
 */

public interface ISocketActContract {

    public interface ISocketActView {
    }

    public interface ISocketActPresenter extends Base.IPresenter {
        void openMainFragment();
    }
}
