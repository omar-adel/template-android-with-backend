package com.example.template.ui.TestSqliteDbflow.simpleitems.presenter;

import android.content.Context;

import com.example.template.model.DataManager;
import com.example.template.model.bean.sqlite.SimpleItem;

/**
 * Created by Net22 on 9/13/2017.
 */

public class DBFlowItemPresenter
        implements DBFlowItemContract.IPresenter {

    Context mContext;
    DBFlowItemContract.IView mView;
    private DataManager mDataManager;

    public DBFlowItemPresenter(Context context, DBFlowItemContract.IView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext
        );

    }


    public void getItemsCount() {
        mView.showItemsCount(
                SimpleItem.getCount()
        );
    }

    public void addItem() {

         SimpleItem simpleItem = new SimpleItem();
        simpleItem.name = "simple item test name";
        mDataManager.insertData(simpleItem);
        getItemsCount();

    }



}
