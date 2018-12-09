package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface ISqliteListContract {
    public interface ISqliteListView {

    }


    public interface ISqliteListPresenter extends Base.IPresenter {

        void openFragment(String source);
    }
}
