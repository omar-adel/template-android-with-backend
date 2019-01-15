package com.example.template.ui.socketTest.presenter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.template.ui.socketTest.SocketMainActivity;
import com.example.template.ui.socketTest.SocketMainFragment;

/**
 * Created by Net22 on 11/13/2017.
 */

public class SocketActPresenter implements ISocketActContract.ISocketActPresenter {

    private final Context mContext;
    ISocketActContract.ISocketActView mView;

    public SocketActPresenter(Context context, ISocketActContract.ISocketActView view) {
        mView = view;
        mContext = context;
    }


    @Override
    public void openMainFragment() {
        Fragment fragment = new SocketMainFragment();
        String fragmentTag = new SocketMainFragment().getClass().getName();
        FragmentManager manager = ((SocketMainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(((SocketMainActivity) mContext).getContainerID(), fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
