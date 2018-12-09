package com.example.template.ui.maps.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.template.ui.maps.MapDisplayActivity;
import com.example.template.ui.maps.MapDisplayFragment;

/**
 * Created by Net22 on 11/13/2017.
 */

public class MapDisplayActivityPresenter   implements  IMapDisplayActivityContract.IMapDisplayActivityContractPresenter {
    private final Context mContext;
    IMapDisplayActivityContract.IMapDisplayActivityContractView mView;

    public MapDisplayActivityPresenter(Context context,
                                       IMapDisplayActivityContract.IMapDisplayActivityContractView view) {
        mView = view;
        mContext = context;
    }




    @Override
    public void openFragmentMap() {
        Fragment fragment = new MapDisplayFragment() ;
        String fragmentTag = fragment.getClass().getName();
        FragmentManager manager = ((MapDisplayActivity)mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(((MapDisplayActivity)mContext).getContainerID(), fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
