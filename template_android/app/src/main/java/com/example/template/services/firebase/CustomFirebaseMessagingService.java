package com.example.template.services.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.example.template.R;
import com.example.template.model.backend.APIService;
import com.example.template.ui.TestRestApi.RestApiListAct;

import modules.general.model.backend.RetrofitClient;
import modules.general.model.shareddata.Prefs;
import modules.general.utils.Constants;
import modules.general.utils.NotificationUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.IOException;

import static modules.general.utils.NotificationUtils.ANDROID_CHANNEL_ID;
import static modules.general.utils.UserInfo.FIREBASE_TOKEN;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    NotificationUtils mNotificationUtils;

    private static final String TAG = CustomFirebaseMessagingService.class.getSimpleName();
    int notification_id = 77;
    Intent intent;
    PendingIntent contentIntent;
    NotificationCompat.Builder mBuilder;
    JSONObject jsonObject;
    String type;
    String message;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        mNotificationUtils = new NotificationUtils(this);
        Log.e("onMessageReceived", " onMessageReceived ");
        Log.e("MessageFcm", " Message Data: " + remoteMessage.getData().get("message"));
        try {

            String message = remoteMessage.getData().get("message");
            JSONObject jsonObject = new JSONObject(message);
            String firebase_json_message = jsonObject.getString("firebase_json_message");
            sendNotification(firebase_json_message);
        } catch (Exception e) {
            Log.e("Exceptio Message Received ", e.toString());
        }


    }

    private void sendNotification(String firebase_json_message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        try {

            jsonObject = new JSONObject(firebase_json_message);
            type = jsonObject.getString("type");
            message = jsonObject.getString("message");


            switch (type) {
                case "normal":
                    intent = new Intent(this, RestApiListAct.class);
                    intent.setAction(String.valueOf(System.currentTimeMillis()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    contentIntent = PendingIntent.getActivity(this, 0,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder = new NotificationCompat.Builder(
                            this, ANDROID_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("fcm normal message received")
                            .setContentText(message)
                            .setSound(defaultSoundUri)
                            .setAutoCancel(true);
                    mBuilder.setContentIntent(contentIntent);
                    notification_id  = (int)System.currentTimeMillis()%10000;
                    mNotificationUtils.getManager().notify(notification_id, mBuilder.build());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String refreshedToken = s;
        Log.e(TAG, "Token Value: " + refreshedToken);

        Prefs.putString(FIREBASE_TOKEN,refreshedToken);

        sendTheRegisteredTokenToWebServer(refreshedToken);
    }

    public void sendTheRegisteredTokenToWebServer(final String token) {

        //clearRetrofit();
        Retrofit retrofit;
        retrofit = RetrofitClient.getClient(Constants.SERVER_URL_PHP);
//          retrofit = RetrofitClient.getClient(Constants.SERVER_URL_NODE);
        //   retrofit = RetrofitClient.getClient(Constants.SERVER_URL_PYTHON);

        final APIService networkCall = retrofit.create(APIService.class);

        networkCall.updateToken(token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                Log.e(" sent token success", "" + response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                            Log.e("Registration Service", "Error :Send Token Failed");
//
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Registration Service", "Error :Send Token Failed");
                        t.printStackTrace();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });


    }

}
