package com.example.template.model.bean.sqlite.querymodels;

import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

/**
 * Created by Net22 on 11/19/2017.
 */

@QueryModel(database = DatabaseModule.class)
public class ItemsQueryModel {

    @Column
    long id;
}
