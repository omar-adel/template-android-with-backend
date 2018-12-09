package com.example.template.ui.TestRestApi.presenter.single;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.template.ui.TestRestApi.RestApiSingleAct;
import com.example.template.ui.TestRestApi.fragments.RestApiSingleFragment;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.INPUT_KEY_CATEGORY_ID;
import static modules.general.utils.Constants.RestApiSingleAction;
import static modules.general.utils.Constants.RestApiSingleSource;

/**
 * Created by Net22 on 11/26/2017.
 */

public class RestApiSinglePresenter implements IRestApiSingleContract.IRestApiSinglePresenter {
    Context mContext;
    IRestApiSingleContract.IRestApiSingleView mView;

    public RestApiSinglePresenter(Context context, IRestApiSingleContract.IRestApiSingleView view) {
        mView = view;
        mContext = context;
    }


    @Override
    public void openFragment(String source, String action, long elementId, long elementCategoryId) {

        Fragment fragment = new RestApiSingleFragment();
        String fragmentTag = fragment.getClass().getName();
        FragmentManager manager = ((RestApiSingleAct) mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(RestApiSingleSource, source);
        bundle.putString(RestApiSingleAction, action);
        bundle.putLong(INPUT_KEY, elementId);
        bundle.putLong(INPUT_KEY_CATEGORY_ID, elementCategoryId);
        fragment.setArguments(bundle);
        ft.replace(((RestApiSingleAct) mContext).getContainerID(), fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    @Override
    public void updateTitle(String title) {
        mView.updateTitle(title);
    }
}