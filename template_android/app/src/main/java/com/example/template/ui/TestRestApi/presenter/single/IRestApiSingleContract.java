package com.example.template.ui.TestRestApi.presenter.single;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface IRestApiSingleContract {

    public interface IRestApiSingleView {

        void updateTitle(String title);
    }


    public interface IRestApiSinglePresenter extends Base.IPresenter {
        void openFragment(String source, String action, long elementId, long elementCategoryId);

        void updateTitle(String title);
    }
}
