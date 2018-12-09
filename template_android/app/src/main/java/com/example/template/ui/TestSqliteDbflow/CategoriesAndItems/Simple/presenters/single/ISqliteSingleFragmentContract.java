package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.single;

import com.example.template.model.bean.sqlite.Categories;
import com.example.template.model.bean.sqlite.Items;

import java.util.ArrayList;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface ISqliteSingleFragmentContract {

    public interface ISqliteSingleFragmentView {
        void showProgress();

        void hideProgress();

        void showSpCategories(ArrayList arrayList);

        void showCategory(Categories category);

        void showItem(Items item);

        void showSuccessSqliteResponse();
    }


    public interface ISqliteSingleFragmentPresenter extends Base.IPresenter {
        void loadElementById(String sourceFragment, long elementId);

        void loadCategories();

        void addCategory(Categories categorySqlite);

        void editCategory(Categories categorySqlite);

        void deleteCategory(Categories categorySqlite);

        void addItem(Items itemSqlite);

        void editItem(Items itemSqlite);

        void deleteItem(Items itemSqlite);
    }
}
