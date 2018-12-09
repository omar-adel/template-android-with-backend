package com.example.template.model.bean.sqlite.querymodels;

import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

/**
 * Created by Net22 on 11/21/2017.
 */

@QueryModel(database = DatabaseModule.class)
public class CategoriesWithOutItemsQueryModel {

    public CategoriesWithOutItemsQueryModel() {

    }

    public CategoriesWithOutItemsQueryModel(long id, String name) {
        this.id = id;

        this.name = name;
    }


    @Override
    public String toString() {
        return "CategoriesWithOutItemsQueryModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Column
    public long id;

    @Column
    public String name;

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
}
