package modules.general.model.backend;


import modules.general.utils.ToStringConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chike on 12/3/2016.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    public static void clearRetrofit() {
        RetrofitClient.retrofit = null;
    }

    public static Retrofit getClient(String baseUrl) {

        okHttpClient = new OkHttpClient.Builder()
                //.readTimeout(1, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                //.connectTimeout(1, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    // .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }



    public static OkHttpClient getAPIOkHttpClient(   ) {

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    //.readTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    //.connectTimeout(1, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
        }

        return okHttpClient;
    }

}
