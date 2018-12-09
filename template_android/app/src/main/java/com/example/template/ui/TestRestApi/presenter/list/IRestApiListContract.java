package com.example.template.ui.TestRestApi.presenter.list;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface IRestApiListContract {
    public interface IRestApiListView {

    }


    public interface IRestApiListPresenter extends Base.IPresenter {

        void openFragment(String source);
    }
}
