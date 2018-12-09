package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Net22 on 11/26/2017.
 */

public class ItemsRestApiResponse implements Parcelable {
    @SerializedName("items")
    private ArrayList<ItemRestApi> results;

    protected ItemsRestApiResponse(Parcel in) {
        results = in.createTypedArrayList(ItemRestApi.CREATOR);
    }

    public static final Parcelable.Creator<ItemsRestApiResponse> CREATOR = new Parcelable.Creator<ItemsRestApiResponse>() {
        @Override
        public ItemsRestApiResponse createFromParcel(Parcel in) {
            return new ItemsRestApiResponse(in);
        }

        @Override
        public ItemsRestApiResponse[] newArray(int size) {
            return new ItemsRestApiResponse[size];
        }
    };

    public ArrayList<ItemRestApi> getResults() {
        return results;
    }

    public void setResults(ArrayList<ItemRestApi> results) {
        this.results = results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
}


