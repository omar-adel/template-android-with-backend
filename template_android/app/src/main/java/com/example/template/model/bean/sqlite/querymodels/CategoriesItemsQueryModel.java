package com.example.template.model.bean.sqlite.querymodels;

import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

/**
 * Created by Net22 on 11/19/2017.
 */

@QueryModel(database = DatabaseModule.class)
public class CategoriesItemsQueryModel {

    @Override
    public String toString() {
        return "CategoriesItemsQueryModel{" +
                "category_id=" + category_id +
                ", item_id=" + item_id +
                ", category_name='" + category_name + '\'' +
                ", item_name='" + item_name + '\'' +
                '}';
    }

    @Column
    long category_id;

    @Column
    long item_id;

    @Column
    String category_name;

    @Column
    String item_name;

}
