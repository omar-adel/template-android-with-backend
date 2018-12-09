package com.example.template.ui.socketTest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.template.R;
import com.example.template.socketServices.SocketIoService;
import modules.general.ui.parentview.ParentActivity;
import com.example.template.ui.socketTest.presenter.ISocketActContract;
import com.example.template.ui.socketTest.presenter.SocketActPresenter;


public class SocketMainActivity extends ParentActivity<SocketActPresenter>

        implements ISocketActContract.ISocketActView {

    private SocketIoService socketIoService;
    private boolean boundedToSocketIoService = false;

    public ServiceConnection socketIoServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            socketIoService = ((SocketIoService.SocketIoServiceLocalBinder) binder).getService();
            boundedToSocketIoService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            boundedToSocketIoService = false;
        }
    };


    private void bindToSocketServices() {

        if (!boundedToSocketIoService) {
            Intent connectionIntentSocketIo = new Intent(this, SocketIoService.class);
            startService(connectionIntentSocketIo);
            bindService(connectionIntentSocketIo, socketIoServiceConnection,
                    Context.BIND_AUTO_CREATE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        bindToSocketServices();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unbind_socket_services();
        finish();
    }


    @Override
    public void configureUI() {
        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();
        getCsTitle().updateTitle(getString(R.string.socket_test));
        getSocketActPresenter().openMainFragment();
    }

    @Override
    public SocketActPresenter injectDependencies() {
        return new SocketActPresenter(this, this);
    }

    public SocketActPresenter getSocketActPresenter() {
        return ((SocketActPresenter) this.getPresenter());
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unbind_socket_services();
    }

    public void unbind_socket_services() {
        if (socketIoServiceConnection != null) {
            if (boundedToSocketIoService) {
                try {
                    unbindService(this.socketIoServiceConnection);
                } catch (IllegalArgumentException e) {
                    Log.e("unbindSocketIoService", e.toString());
                }
                boundedToSocketIoService = false;

            }
        }
    }

}
