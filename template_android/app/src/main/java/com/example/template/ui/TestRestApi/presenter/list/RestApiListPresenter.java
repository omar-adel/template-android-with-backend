package com.example.template.ui.TestRestApi.presenter.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.template.ui.TestRestApi.RestApiListAct;
import com.example.template.ui.TestRestApi.fragments.RestApiListFragment;

import static modules.general.utils.Constants.RestApiListSource;

/**
 * Created by Net22 on 11/26/2017.
 */

public class RestApiListPresenter implements IRestApiListContract.IRestApiListPresenter {
    Context mContext;
    IRestApiListContract.IRestApiListView mView;

    public RestApiListPresenter(Context context, IRestApiListContract.IRestApiListView view) {
        mView = view;
        mContext = context;
    }



    @Override
    public void openFragment(String source) {

        Fragment fragment = new RestApiListFragment();
        String fragmentTag = fragment.getClass().getName();
        FragmentManager manager = ((RestApiListAct) mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(RestApiListSource, source);
        fragment.setArguments(bundle);
        ft.replace(((RestApiListAct) mContext).getContainerID(), fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }
}
