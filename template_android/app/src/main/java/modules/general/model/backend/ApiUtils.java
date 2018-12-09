package modules.general.model.backend;


import com.example.template.model.backend.APIService;

import okhttp3.OkHttpClient;

import static modules.general.utils.Constants.SERVER_URL_PHP;

/**
 * Created by Chike on 12/4/2016.
 */

public class ApiUtils {

    private ApiUtils() {
    }

    public static APIService getAPIService() {

        //clearRetrofit();
        return RetrofitClient.getClient(SERVER_URL_PHP).create(APIService.class);
    }

    public static OkHttpClient getAPIOkHttpClient() {
        return RetrofitClient.getAPIOkHttpClient();
    }
}
