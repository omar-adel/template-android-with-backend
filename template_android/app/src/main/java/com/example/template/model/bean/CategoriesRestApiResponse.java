package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Net22 on 11/26/2017.
 */

public class CategoriesRestApiResponse implements Parcelable {
    @SerializedName("categories")
    private ArrayList<CategoryRestApi> results;

    protected CategoriesRestApiResponse(Parcel in) {
        results = in.createTypedArrayList(CategoryRestApi.CREATOR);
    }

    public static final Parcelable.Creator<CategoriesRestApiResponse> CREATOR = new Parcelable.Creator<CategoriesRestApiResponse>() {
        @Override
        public CategoriesRestApiResponse createFromParcel(Parcel in) {
            return new CategoriesRestApiResponse(in);
        }

        @Override
        public CategoriesRestApiResponse[] newArray(int size) {
            return new CategoriesRestApiResponse[size];
        }
    };

    public ArrayList<CategoryRestApi> getResults() {
        return results;
    }

    public void setResults(ArrayList<CategoryRestApi> results) {
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


