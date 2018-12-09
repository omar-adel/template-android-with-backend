package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.ComplexOperations.presenter;


import java.util.ArrayList;
import java.util.List;

import com.mvp_base.Base;

/**
 * Created by Net22 on 9/13/2017.
 */

public interface ComplexOperationsContract {

    interface IView {

        void showResult(String result);

        void showResult(List arrayList, String className);

    }


    interface IPresenter extends Base.IPresenter {

        void onGetCategoriesWithoutItems();

        void onGetCategoriesWithItems();

        void onGetItemsByCategoryId(long category_id);

        void onGetItemsByCategoriesIds(ArrayList<Long> ids);

        void onGetCategoriesWhereIdInIds(ArrayList<Long> ids);

        void onGetItemsWhereIdInCategoriesIds(ArrayList<Long> ids);

        void onGetCustomListCategoriesItemsJoin();


    }

}
