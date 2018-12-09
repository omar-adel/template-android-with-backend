package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.SqliteListAct;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.fragments.SqliteListFragment;

import static modules.general.utils.Constants.SqliteListSource;

/**
 * Created by Net22 on 11/26/2017.
 */

public class SqliteListPresenter implements ISqliteListContract.ISqliteListPresenter {
    Context mContext;
    ISqliteListContract.ISqliteListView mView;

    public SqliteListPresenter(Context context, ISqliteListContract.ISqliteListView view) {
        mView = view;
        mContext = context;
    }


    @Override
    public void openFragment(String source) {

        Fragment fragment = new SqliteListFragment();
        String fragmentTag = fragment.getClass().getName();
        FragmentManager manager = ((SqliteListAct) mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(SqliteListSource, source);
        fragment.setArguments(bundle);
        ft.replace(((SqliteListAct) mContext).getContainerID(), fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }
}
