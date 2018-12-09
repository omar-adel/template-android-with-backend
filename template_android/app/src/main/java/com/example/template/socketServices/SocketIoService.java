package com.example.template.socketServices;

/**
 * Created by Net22 on 10/29/2017.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.template.App;
import com.example.template.R;
import com.example.template.ui.socketTest.SocketMainActivity;
import modules.general.utils.NotificationUtils;

import org.greenrobot.eventbus.EventBus;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static modules.general.utils.NotificationUtils.ANDROID_CHANNEL_ID;

public class SocketIoService extends Service {

    NotificationUtils mNotificationUtils;
    private Socket mSocket;
    public static final String TAG = SocketIoService.class.getSimpleName();
    private int notification_service_id = 1;
    private int notification_id = 999;
    public final IBinder localBinder = new SocketIoService.SocketIoServiceLocalBinder();

    public class SocketIoServiceLocalBinder extends Binder {
        public SocketIoService getService() {
            return SocketIoService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "on created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Toast.makeText(this, "start command", Toast.LENGTH_SHORT).show();
        mNotificationUtils = new NotificationUtils(this);
        connectSocketIo();
        startForeground(notification_service_id, sendMainServiceNotification(getApplicationContext(),
                notification_service_id++, "socket nodejs service"
                , "socket nodejs service running"));
        return START_STICKY;
    }

    private void connectSocketIo() {
        mSocket = ((App) getApplication()).getSocket();
        mSocket.on("newMessageFromWeb", onNewMessage);
        mSocket.connect();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String message = args[0].toString();
            Log.e(TAG, "onNewMessage: new message ");
            EventBus.getDefault().post(message);
            sendReceivedNotification(getApplicationContext(), notification_id++, "new message", message, null);
        }
    };

    private Notification sendMainServiceNotification(Context context, int uniqueId, String title, String contentText) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ANDROID_CHANNEL_ID);
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(contentText);
        builder.setGroup("socketio notification");
        builder.setDefaults(android.app.Notification.DEFAULT_ALL);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .setSummaryText(title)
                .setBigContentTitle(title)
                .bigText(contentText)
        );

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setShowWhen(true);
        android.app.Notification notification = builder.build();
        mNotificationUtils.getManager().notify(uniqueId, notification);

        return notification;

    }


    private void sendReceivedNotification(Context context, int uniqueId,
                                          String title, String contentText,
                                          @Nullable Class<?> resultClass) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ANDROID_CHANNEL_ID);
        builder.setAutoCancel(true);

        builder.setContentTitle(title);
        builder.setContentText(contentText);
        builder.setGroup("socketio received notification");
        builder.setDefaults(android.app.Notification.DEFAULT_ALL);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .setSummaryText(title)
                .setBigContentTitle(title)
                .bigText(contentText)
        );

        Intent requestsViewIntent = new Intent(context, SocketMainActivity.class);
        requestsViewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        requestsViewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent requestsViewPending = PendingIntent.getActivity(context, uniqueId, requestsViewIntent, 0);
        builder.setContentIntent(requestsViewPending);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setShowWhen(true);
        android.app.Notification notification = builder.build();
        mNotificationUtils.getManager().notify(uniqueId, notification);
    }


    @Override
    public void onDestroy() {
        //stop thread and socket connection here
        mSocket.disconnect();
        mSocket.off("newMessageReceived", onNewMessage);
    }


}