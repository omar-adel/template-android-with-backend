package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list;

import android.content.Context;

import com.example.template.model.DataManager;
import com.example.template.model.bean.sqlite.Items;
import com.example.template.model.bean.sqlite.querymodels.CategoriesWithOutItemsQueryModel;
import modules.general.model.db.SqliteCallBack;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.fragments.SqliteListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static modules.general.utils.Constants.SqliteSourceCategories;
import static modules.general.utils.Constants.SqliteSourceItems;

/**
 * Created by Net22 on 11/26/2017.
 */

public class SqliteListFragmentPresenter extends SqliteCallBack implements ISqliteListFragmentContract.ISqliteListFragmentPresenter
          {
    Context mContext;
    ISqliteListFragmentContract.ISqliteListFragmentView mView;
    private DataManager mDataManager;

    public SqliteListFragmentPresenter(Context context, ISqliteListFragmentContract.ISqliteListFragmentView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext);
        mDataManager.setPresenterSqliteCallBack(this);
    }


    @Override
    public void loadList(String source) {

        mView.showProgress();
        if (source.equals(SqliteSourceCategories)) {
            mDataManager.getDbHelper().getCategoriesWithoutItems(this);
        } else if (source.equals(SqliteSourceItems)) {
            mDataManager.getAll(new Items(), this);
         }

    }

    @Override
    public void loadItemsByCategory(long category_id) {

        Items items = new Items();
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("category_id", String.valueOf(category_id));
        items.setItemWithCustomData(hashMap);
        ArrayList<Items> itemsArrayList = (ArrayList<Items>) mDataManager
                .getItemsArrWithCustomData(
                items);
        ((SqliteListFragment) mView).showList(itemsArrayList);

     }




    @Override
    public void onDBDataListLoaded(ArrayList data  , String localDbOperation) {

        mView.hideProgress();
        if (localDbOperation.equals("getCategoriesWithoutItems")) {
            if (((SqliteListFragment) mView).getSourceFragment().equals(SqliteSourceCategories)) {
                ((SqliteListFragment) mView).showList(new ArrayList<CategoriesWithOutItemsQueryModel>(data));
            } else if (((SqliteListFragment) mView).getSourceFragment().equals(SqliteSourceItems)) {
                ((SqliteListFragment) mView).showSpCategories(new ArrayList<CategoriesWithOutItemsQueryModel>(data));
            }

        } else if (localDbOperation.equals("getAllItems")) {
            ((SqliteListFragment) mView).showList(new ArrayList<Items>(data));
        }


    }


}
