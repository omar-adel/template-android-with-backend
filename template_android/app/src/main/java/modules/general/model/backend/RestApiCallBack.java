package modules.general.model.backend;



import modules.general.model.backend.listeners.IRestApiCallBack;

import java.util.ArrayList;

/**
 * Created by mohan on 4/10/16.
 */

public  abstract class RestApiCallBack implements IRestApiCallBack {



    @Override
    public void onDataListLoaded(ArrayList data, String url) {

    }

    @Override
    public void onDataObjectLoaded(Object data, String url) {

    }

    @Override
    public void onNetworkError(String message, String url) {

    }

    @Override
    public void onNoInternet() {

    }
}