package com.example.template.model.bean.sqlite;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import io.reactivex.annotations.Nullable;
import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import modules.general.model.bean.sqlite.BaseModelWithIData;


/**
 * Created by Net22 on 9/24/2017.
 */


@Table(database = DatabaseModule.class)
public class CacheApi extends BaseModelWithIData implements   Parcelable {
    @Column
    @PrimaryKey(autoincrement = true)
    private Long id = 0l;
    @Column
    private String url;
    @Column
    private String params;
    @Column
    private String response;
    @Column
    private String beanName;
    @Column
    private long date;
    @Column
    private String objectOfArrayBeanName;


        public CacheApi() {
        }

        public CacheApi(Long id) {
            this.id = id;
        }

         public CacheApi(Long id, String url, String params, String response , String beanName
                , long date, String objectOfArrayBeanName) {
            this.id = id;
            this.url = url;
            this.params = params;
            this.response = response;
            this.beanName = beanName;
            this.date = date;
             this.objectOfArrayBeanName = objectOfArrayBeanName;
         }

    public CacheApi(String url, String params) {
        this.url = url;
        this.params=params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(params);
        parcel.writeString(response);
        parcel.writeString(beanName);
        parcel.writeLong(date);
        parcel.writeString(objectOfArrayBeanName);
    }

    protected CacheApi(Parcel in) {
        url = in.readString();
        params = in.readString();
        response = in.readString();
        beanName = in.readString();
        date = in.readLong();
        objectOfArrayBeanName = in.readString();
    }

    public static final Creator<CacheApi> CREATOR = new Creator<CacheApi>() {
        @Override
        public CacheApi createFromParcel(Parcel in) {
            return new CacheApi(in);
        }

        @Override
        public CacheApi[] newArray(int size) {
            return new CacheApi[size];
        }
    };

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

         public String getUrl() {
            return url;
        }

        /** Not-null value; ensure this value is available before it is saved to the database. */
        public void setUrl( String url) {
            this.url = url;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }


        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }


        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }


    public String getObjectOfArrayBeanName() {
        return objectOfArrayBeanName;
    }

    public void setObjectOfArrayBeanName(String objectOfArrayBeanName) {
        this.objectOfArrayBeanName = objectOfArrayBeanName;
    }

    @Override
    public void insertData(Object object, SqliteCallBack sqliteCallBack) {
      CacheApi cacheApi=(CacheApi)object;
      cacheApi.insert();
    }

    @Override
    public List getAll( ) {
        List<CacheApi> cacheApiList = SQLite.select().from(CacheApi.class)
                .queryList();
         return cacheApiList ;
     }

}