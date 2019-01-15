package com.example.template;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.example.template.model.DataManager;
import modules.general.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import modules.general.utils.LanguageUtil;


/**
 * Created by mohan on 6/10/16.
 */

public class App extends Application {

    //socketio
    private Socket mSocket;

    public Socket getSocket() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket(Constants.SERVER_SocketIo_URL);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return mSocket;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
         (DataManager.getInstance(this)).initData();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        LanguageUtil.setAppLanguage(getApplicationContext(),  LanguageUtil.ENGLISH_LANGUAGE);

        //socketio
//        try {
//            mSocket=IO.socket(Constants.SERVER_SocketIo_URL);
//            Intent intent=new Intent(getApplicationContext(),SocketIoService.class);
//            startService(intent);
//         } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


    }



}
