package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by net22 on 11/27/2017.
 */

public class EditRestApiResponse implements Parcelable {


    @SerializedName("success")
    public String success;


    public EditRestApiResponse() {

    }

    public EditRestApiResponse(String success) {
        this.success = success;
    }


    protected EditRestApiResponse(Parcel in) {
        success = in.readString();
    }

    public static final Creator<EditRestApiResponse> CREATOR = new Creator<EditRestApiResponse>() {
        @Override
        public EditRestApiResponse createFromParcel(Parcel in) {
            return new EditRestApiResponse(in);
        }

        @Override
        public EditRestApiResponse[] newArray(int size) {
            return new EditRestApiResponse[size];
        }
    };

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(success);
    }
}


