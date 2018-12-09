package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Net22 on 11/29/2017.
 */

public class ItemRestApiResponse implements Parcelable {

    @SerializedName("item")
    private ItemRestApi itemRestApi;

    protected ItemRestApiResponse(Parcel in) {
        itemRestApi = in.readParcelable(ItemRestApi.class.getClassLoader());
    }

    public static final Creator<ItemRestApiResponse> CREATOR = new Creator<ItemRestApiResponse>() {
        @Override
        public ItemRestApiResponse createFromParcel(Parcel in) {
            return new ItemRestApiResponse(in);
        }

        @Override
        public ItemRestApiResponse[] newArray(int size) {
            return new ItemRestApiResponse[size];
        }
    };

    public ItemRestApi getItemRestApi() {
        return itemRestApi;
    }

    public void setCategoryRestApi(ItemRestApi itemRestApi) {
        this.itemRestApi = itemRestApi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(itemRestApi, i);
    }
}


