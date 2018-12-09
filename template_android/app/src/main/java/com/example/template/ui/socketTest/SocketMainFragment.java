package com.example.template.ui.socketTest;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.template.App;
import com.example.template.R;
import com.example.template.ui.socketTest.presenter.ISocketFrgContract;
import com.example.template.ui.socketTest.presenter.SocketFrgPresenter;

import com.mvp_base.BaseSupportFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.Socket;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * A chat fragment containing messages view and input form.
 */
public class SocketMainFragment extends BaseSupportFragment<SocketFrgPresenter> implements ISocketFrgContract.ISocketFrgView {

    @BindView(R.id.send_button)
    ImageView sendButton;

    public SocketMainFragment() {
        super();
    }

    //socketio node
    private Socket mSocket;

    private void attemptSend() {
        String message = "getAllCategories";
        // perform the sending message attempt.
        //socketio
        mSocket = ((App) getContainerActivity().getApplicationContext()).getSocket();
        mSocket.emit("newMessageFromAndroid", message);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessageReceived(final String message) {
        getContainerActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContainerActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                Log.e("newMessageFromWeb", message);
            }
        });

    }

    @Override
    public int getLayoutResource() {
        return R.layout.frg_socketio_main;
    }

    @Override
    public void configureUI() {

    }

    @Override
    public SocketFrgPresenter injectDependencies() {
        return new SocketFrgPresenter(getContainerActivity(), this);
    }

    @OnClick(R.id.send_button)
    public void onViewClicked() {
        attemptSend();
    }
}

