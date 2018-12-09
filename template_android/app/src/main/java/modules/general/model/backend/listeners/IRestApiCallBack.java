package modules.general.model.backend.listeners;


import java.util.ArrayList;

/**
 * Created by mohan on 4/10/16.
 */

public interface IRestApiCallBack<T> {
    void onDataListLoaded(ArrayList<T> data, String url);

    void onDataObjectLoaded(T data, String url);

    void onNetworkError(String message, String url);

    void onNoInternet();
}