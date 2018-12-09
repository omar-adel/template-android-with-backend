package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Net22 on 11/29/2017.
 */

public class CategoryRestApiResponse implements Parcelable {
    @SerializedName("category")
    private CategoryRestApi categoryRestApi;

    protected CategoryRestApiResponse(Parcel in) {
        categoryRestApi = in.readParcelable(CategoryRestApi.class.getClassLoader());
    }

    public static final Creator<CategoryRestApiResponse> CREATOR = new Creator<CategoryRestApiResponse>() {
        @Override
        public CategoryRestApiResponse createFromParcel(Parcel in) {
            return new CategoryRestApiResponse(in);
        }

        @Override
        public CategoryRestApiResponse[] newArray(int size) {
            return new CategoryRestApiResponse[size];
        }
    };

    public CategoryRestApi getCategoryRestApi() {
        return categoryRestApi;
    }

    public void setCategoryRestApi(CategoryRestApi categoryRestApi) {
        this.categoryRestApi = categoryRestApi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(categoryRestApi, i);
    }
}


