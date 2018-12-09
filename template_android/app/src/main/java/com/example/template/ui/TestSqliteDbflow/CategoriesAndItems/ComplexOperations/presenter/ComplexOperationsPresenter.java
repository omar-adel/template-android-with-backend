package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.ComplexOperations.presenter;

import android.content.Context;

import com.example.template.model.DataManager;
import com.example.template.model.bean.sqlite.Categories;
import com.example.template.model.bean.sqlite.Items;
import com.example.template.model.bean.sqlite.querymodels.CategoriesItemsQueryModel;
import com.example.template.model.bean.sqlite.querymodels.CategoriesWithOutItemsQueryModel;
import modules.general.model.db.SqliteCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static modules.general.model.db.Constants.DBMethodGetAll;

/**
 * Created by Net22 on 9/13/2017.
 */

public class ComplexOperationsPresenter extends SqliteCallBack
        implements ComplexOperationsContract.IPresenter   {

    Context mContext;
    ComplexOperationsContract.IView mView;
    private DataManager mDataManager;

    public ComplexOperationsPresenter(Context context, ComplexOperationsContract.IView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext
        );
        mDataManager.setPresenterSqliteCallBack(this);

    }

    @Override
    public void onGetCategoriesWithoutItems() {
        mDataManager.getDbHelper().getCategoriesWithoutItems(this);

    }

    @Override
    public void onGetCategoriesWithItems() {
        mDataManager.getAll(new Categories(), this);
     }

    @Override
    public void onGetItemsByCategoryId(long category_id) {

        Items items = new Items();
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("category_id", String.valueOf(category_id));
        items.setItemWithCustomData(hashMap);
        mDataManager.getItemsArrWithCustomData(
                items,this);
        }

    @Override
    public void onGetItemsByCategoriesIds(ArrayList<Long> ids) {
        mDataManager.getDbHelper().getItemsByCategoriesIds(ids,this);
    }

    @Override
    public void onGetCategoriesWhereIdInIds(ArrayList<Long> ids) {
        mDataManager.getDbHelper().getCategoriesWhereIdInIds(ids,this);
    }

    @Override
    public void onGetItemsWhereIdInCategoriesIds(ArrayList<Long> ids) {
        mDataManager.getDbHelper().getItemsWhereIdInCategoriesIds(ids,this);
    }

    @Override
    public void onGetCustomListCategoriesItemsJoin() {
        mDataManager.getDbHelper().getCustomListCategoriesItemsJoin(this);

    }



    @Override
    public void onDBDataListLoaded(ArrayList data  ) {

        if (data.size() > 0) {
                mView.showResult(data, Items.class.getName());
        } else {
            mView.showResult("empty");
        }

    }

    @Override
    public void onDBDataListLoaded(ArrayList data , String localDbOperation) {

        if (data.size() > 0) {
            if ( (data.get(0) instanceof Categories)) {
                mView.showResult(data, Categories.class.getName());
            } else if ( (data.get(0) instanceof Items)) {
                mView.showResult(data, Items.class.getName());
            } else if ( (data.get(0) instanceof CategoriesItemsQueryModel)) {
                mView.showResult(data, CategoriesItemsQueryModel.class.getName());
            } else if ( (data.get(0) instanceof CategoriesWithOutItemsQueryModel)) {
                mView.showResult(data, CategoriesWithOutItemsQueryModel.class.getName());
            }
        } else {
            mView.showResult("empty");
        }

    }


}
