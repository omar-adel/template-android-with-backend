package com.example.template.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Net22 on 11/19/2017.
 */

public class ItemRestApi implements Parcelable {


    protected ItemRestApi(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        category_id = in.readLong();
    }

    public static final Creator<ItemRestApi> CREATOR = new Creator<ItemRestApi>() {
        @Override
        public ItemRestApi createFromParcel(Parcel in) {
            return new ItemRestApi(in);
        }

        @Override
        public ItemRestApi[] newArray(int size) {
            return new ItemRestApi[size];
        }
    };


    @SerializedName("id")
    public long id = 0l;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("category_id")
    public long category_id = 0l;

    public ItemRestApi() {

    }

    public ItemRestApi(long id, String name, String description, long category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category_id = category_id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeLong(category_id);
    }
}
