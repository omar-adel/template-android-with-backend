package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.single;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.SqliteSingleAct;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.fragments.SqliteSingleFragment;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.INPUT_KEY_CATEGORY_ID;
import static modules.general.utils.Constants.SqliteSingleAction;
import static modules.general.utils.Constants.SqliteSingleSource;

/**
 * Created by Net22 on 11/26/2017.
 */

public class SqliteSinglePresenter implements ISqliteSingleContract.ISqliteSinglePresenter {
    Context mContext;
    ISqliteSingleContract.ISqliteSingleView mView;

    public SqliteSinglePresenter(Context context, ISqliteSingleContract.ISqliteSingleView view) {
        mView = view;
        mContext = context;
    }




    @Override
    public void openFragment(String source, String action, long elementId, long elementCategoryId) {

        Fragment fragment = new SqliteSingleFragment();
        String fragmentTag = fragment.getClass().getName();
        FragmentManager manager = ((SqliteSingleAct) mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(SqliteSingleSource, source);
        bundle.putString(SqliteSingleAction, action);
        bundle.putLong(INPUT_KEY, elementId);
        bundle.putLong(INPUT_KEY_CATEGORY_ID, elementCategoryId);
        fragment.setArguments(bundle);
        ft.replace(((SqliteSingleAct) mContext).getContainerID(), fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    @Override
    public void updateTitle(String title) {
        mView.updateTitle(title);
    }
}