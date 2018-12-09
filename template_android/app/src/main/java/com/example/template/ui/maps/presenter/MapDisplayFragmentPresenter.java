package com.example.template.ui.maps.presenter;

import android.content.Context;

/**
 * Created by Net22 on 11/13/2017.
 */

public class MapDisplayFragmentPresenter   implements  IMapDisplayFragmentContract.IMapDisplayFragemntContractPresenter {
    private final Context mContext;
    IMapDisplayFragmentContract.IMapDisplayFragmentContractView mView;

    public MapDisplayFragmentPresenter(Context context, IMapDisplayFragmentContract.IMapDisplayFragmentContractView view) {
        mView = view;
        mContext = context;
    }



}