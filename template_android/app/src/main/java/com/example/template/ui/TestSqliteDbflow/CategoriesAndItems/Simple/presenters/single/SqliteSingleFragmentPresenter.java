package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.single;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import com.example.template.model.DataManager;
import com.example.template.model.bean.sqlite.Categories;
import com.example.template.model.bean.sqlite.Items;
import modules.general.model.db.SqliteCallBack;

import com.example.template.model.bean.sqlite.querymodels.CategoriesWithOutItemsQueryModel;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.fragments.SqliteSingleFragment;

import java.util.ArrayList;

import static modules.general.utils.Constants.SqliteSourceCategories;
import static modules.general.utils.Constants.SqliteSourceItems;

/**
 * Created by Net22 on 11/26/2017.
 */

public class SqliteSingleFragmentPresenter extends SqliteCallBack
        implements ISqliteSingleFragmentContract.ISqliteSingleFragmentPresenter {
    Context mContext;
    ISqliteSingleFragmentContract.ISqliteSingleFragmentView mView;
    private DataManager mDataManager;

    public SqliteSingleFragmentPresenter(Context context, ISqliteSingleFragmentContract.ISqliteSingleFragmentView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext);
         mDataManager.setPresenterSqliteCallBack(this);
    }


    @Override
    public void loadElementById(String sourceFragment, long elementId) {

         if (sourceFragment.equals(SqliteSourceCategories)) {
            Categories categories = (Categories) mDataManager.getItemByID(new Categories(),
                    ((int) elementId));
            ((SqliteSingleFragment) mView).showCategory(categories);

        } else if (sourceFragment.equals(SqliteSourceItems)) {
            Items item = (Items) mDataManager.getItemByID(new Items(),
                    ((int) elementId));
            ((SqliteSingleFragment) mView).showItem(item);
         }

    }

    @Override
    public void loadCategories() {
        mView.showProgress();
        mDataManager.getDbHelper().getCategoriesWithoutItems(this);
    }

    @Override
    public void addCategory(Categories categorySqlite) {

        mView.showProgress();
//        Categories category = new Categories();
//        category.name = categorySqlite.getName();
        mDataManager.insertData(categorySqlite, this);
     }

    @Override
    public void editCategory(Categories categorySqlite) {
        mView.showProgress();
//        Categories category = new Categories();
//        category.id = categorySqlite.getId();
//        category.name = categorySqlite.getName();
         mDataManager.updateData(categorySqlite, this);
     }

    @Override
    public void deleteCategory(Categories categorySqlite) {
        mView.showProgress();
//        Categories category = new Categories();
//        category.id = categorySqlite.getId();
         mDataManager.deleteData(categorySqlite, this);

    }

    @Override
    public void addItem(Items itemSqlite) {
        mView.showProgress();
//        Items item = new Items();
//        item.name = itemSqlite.getName();
//        item.description = itemSqlite.getDescription();
//        item.category_id = itemSqlite.getCategory_id();
         mDataManager.insertData(itemSqlite, this);

    }

    @Override
    public void editItem(Items itemSqlite) {
        mView.showProgress();
//        Items item = new Items();
//        item.id = itemSqlite.getId();
//        item.name = itemSqlite.getName();
//        item.description = itemSqlite.getDescription();
//        item.category_id = itemSqlite.getCategory_id();
        mDataManager.updateData(itemSqlite, this);
     }

    @Override
    public void deleteItem(Items itemSqlite) {
        mView.showProgress();
//        Items item = new Items();
//        item.id = itemSqlite.getId();
        mDataManager.deleteData(itemSqlite, this);
    }


    @Override
    public void onDBDataListLoaded(ArrayList data , String localDbOperation) {
        mView.hideProgress();

        ((SqliteSingleFragment) mView).showSpCategories(new ArrayList<CategoriesWithOutItemsQueryModel>(data));

    }

    @Override
    public void onDBDataObjectLoaded(Object data , String localDbOperation) {

        mView.hideProgress();
            if (localDbOperation.equals("insertCategory")) {
            ((SqliteSingleFragment) mView).showSuccessSqliteResponse();
        } else if (localDbOperation.equals("updateCategory")) {
            ((SqliteSingleFragment) mView).showSuccessSqliteResponse();
        } else if (localDbOperation.equals("deleteCategory")) {
            ((SqliteSingleFragment) mView).showSuccessSqliteResponse();
            ((AppCompatActivity) (mContext)).finish();
        } else if (localDbOperation.equals("insertItem")) {
            ((SqliteSingleFragment) mView).showSuccessSqliteResponse();
        } else if (localDbOperation.equals("updateItem")) {
            ((SqliteSingleFragment) mView).showSuccessSqliteResponse();
        } else if (localDbOperation.equals("deleteItem")) {
            ((SqliteSingleFragment) mView).showSuccessSqliteResponse();
            ((AppCompatActivity) (mContext)).finish();
        }

    }
}