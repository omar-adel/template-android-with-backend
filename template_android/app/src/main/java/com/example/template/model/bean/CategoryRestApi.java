package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Net22 on 11/19/2017.
 */

public class CategoryRestApi implements Parcelable {


    @SerializedName("id")
    public long id = 0l;


    @SerializedName("name")
    public String name;


    public CategoryRestApi() {

    }

    public CategoryRestApi(long id, String name) {
        this.id = id;
        this.name = name;
    }


    protected CategoryRestApi(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<CategoryRestApi> CREATOR = new Creator<CategoryRestApi>() {
        @Override
        public CategoryRestApi createFromParcel(Parcel in) {
            return new CategoryRestApi(in);
        }

        @Override
        public CategoryRestApi[] newArray(int size) {
            return new CategoryRestApi[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
    }
}
