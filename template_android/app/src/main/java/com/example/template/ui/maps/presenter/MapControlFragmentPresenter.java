package com.example.template.ui.maps.presenter;

import android.content.Context;

/**
 * Created by Net22 on 11/13/2017.
 */

public class MapControlFragmentPresenter
        implements  IMapControlFragmentContract.IMapControlFragemntContractPresenter {
    private final Context mContext;
    IMapControlFragmentContract.IMapControlFragmentContractView mView;

    public MapControlFragmentPresenter(Context context, IMapControlFragmentContract.IMapControlFragmentContractView view) {
        mView = view;
        mContext = context;
    }




}