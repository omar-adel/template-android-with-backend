package com.example.template.ui.splash.presenter;

import android.content.Context;

/**
 * Created by Net22 on 9/19/2017.
 */

public class SplashPresenter implements ISplashContract.ISplashPresenter {
    private final Context mContext;
    ISplashContract.ISplashView mView;

    public SplashPresenter(Context context, ISplashContract.ISplashView view) {
        mView = view;
        mContext = context;
    }



}