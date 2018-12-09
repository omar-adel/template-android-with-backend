package com.example.template.ui.socketTest.presenter;

import android.content.Context;

/**
 * Created by Net22 on 11/13/2017.
 */

public class SocketFrgPresenter implements ISocketFrgContract.ISocketFrgPresenter {


    private final Context mContext;
    ISocketFrgContract.ISocketFrgView mView;

    public SocketFrgPresenter(Context context, ISocketFrgContract.ISocketFrgView view) {
        mView = view;
        mContext = context;
    }

}
