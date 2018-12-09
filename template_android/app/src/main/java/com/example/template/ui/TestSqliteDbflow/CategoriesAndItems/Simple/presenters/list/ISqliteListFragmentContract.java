package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list;

import java.util.ArrayList;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface ISqliteListFragmentContract {
    public interface ISqliteListFragmentView {
        void showProgress();

        void hideProgress();

        void showList(ArrayList arrayList);

        void showSpCategories(ArrayList arrayList);
    }


    public interface ISqliteListFragmentPresenter extends Base.IPresenter {
        void loadList(String source);

        void loadItemsByCategory(long category_id);
    }
}
