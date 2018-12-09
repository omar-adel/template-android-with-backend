/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.template.model.db;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import modules.general.model.bean.sqlite.BaseModelWithIData;

import com.example.template.model.bean.sqlite.Categories;
import com.example.template.model.bean.sqlite.Categories_Table;
import com.example.template.model.bean.sqlite.Items;
import com.example.template.model.bean.sqlite.Items_Table;
import com.example.template.model.bean.sqlite.Note;
import com.example.template.model.bean.sqlite.NoteType;
import com.example.template.model.bean.sqlite.SimpleItem;
import com.example.template.model.bean.sqlite.querymodels.CategoriesItemsQueryModel;
import com.example.template.model.bean.sqlite.querymodels.CategoriesWithOutItemsQueryModel;

import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.listener.IDataHelper;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by janisharali on 08/12/16.
 */

public class DbHelper
        implements IDataHelper {

     Context dbContext;

    public DbHelper(Context context) {
        dbContext = context;
    }

    @Override
    public void insertData(Object object) {
        ((BaseModelWithIData)object).insertData(object);
    }

    @Override
    public void updateData(Object object) {
        ((BaseModelWithIData)object).updateData(object);
    }

    @Override
    public void saveData(Object object) {
        ((BaseModelWithIData)object).saveData(object);
    }

    @Override
    public void deleteData(Object object) {
        ((BaseModelWithIData)object).deleteData(object);
    }

    @Override
    public void deleteAll(Object object) {
        ((BaseModelWithIData)object).deleteAll();

    }

    @Override
    public void insertData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).insertData(object,sqliteCallBack);
    }

    @Override
    public void updateData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).updateData(object,sqliteCallBack);

    }

    @Override
    public void deleteData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).deleteData(object,sqliteCallBack);

    }

    @Override
    public void getAll(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getAll(sqliteCallBack);
    }

    @Override
    public List getAll(Object object) {
        return ((BaseModelWithIData)object).getAll();
    }




    @Override
    public Object getItemByID(Object object, int id) {
        return ((BaseModelWithIData)object).getItemByID(id);
    }

    @Override
    public void getItemByID(Object object, int id, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getItemByID(id,sqliteCallBack);
    }

    @Override
    public void setItemWithCustomData(HashMap hashMap) {

    }


    @Override
    public void getItemWithCustomData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getItemWithCustomData(sqliteCallBack);

    }

    @Override
    public List getItemsArrWithCustomData(Object object) {
        return ((BaseModelWithIData)object).getItemsArrWithCustomData();
    }

    @Override
    public void getItemsArrWithCustomData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getItemsArrWithCustomData(sqliteCallBack);
    }

    @Override
    public Object getItemWithCustomData(Object object) {

        return  ((BaseModelWithIData)object).getItemWithCustomData();
    }

    @Override
    public void deleteItemWithCustomData(Object object) {
        ((BaseModelWithIData)object).deleteItemWithCustomData();

    }


    public void getCategoriesWithoutItems(final SqliteCallBack sqliteCallBack) {

        List<CategoriesWithOutItemsQueryModel> categoriesWithOutItemsQueryModels = SQLite.select(
                Categories_Table.id,
                Categories_Table.name)
                .from(Categories.class)
                .queryCustomList(CategoriesWithOutItemsQueryModel.class);

        sqliteCallBack.onDBDataListLoaded((ArrayList) categoriesWithOutItemsQueryModels
                , "getCategoriesWithoutItems");

    }




    public void getItemsByCategoriesIds(ArrayList<Long> ids, final SqliteCallBack mSqliteCallBack) {


        SQLite.select().from(Items.class)
                .where(Items_Table.category_id
                        .in(ids)
                )
                .async()
                .queryResultCallback(
                        new QueryTransaction.QueryResultCallback<Items>() {
                            @Override
                            public void onQueryResult(final QueryTransaction<Items> transaction, @NonNull final CursorResult<Items> tResult) {
                                mSqliteCallBack.onDBDataListLoaded((ArrayList) tResult.toListClose()
                                        , "getItemsByCategoriesIds");
                            }
                        }
                ).execute();


    }

    public void getCategoriesWhereIdInIds(ArrayList<Long> ids, final SqliteCallBack sqliteCallBack) {

        SQLite.select().from(Categories.class)
                .where(Categories_Table.id
                        .in(ids))
                .async()
                .queryResultCallback(
                        new QueryTransaction.QueryResultCallback<Categories>() {
                            @Override
                            public void onQueryResult(final QueryTransaction<Categories> transaction, @NonNull final CursorResult<Categories> tResult) {
                                sqliteCallBack.onDBDataListLoaded(
                                        (ArrayList) tResult.toListClose()
                                        , "getCategoriesWhereIdInIds");
                            }
                        }
                ).execute();


    }

    public void getItemsWhereIdInCategoriesIds(ArrayList<Long> ids, final SqliteCallBack sqliteCallBack) {

        SQLite.select().from(Items.class)
                .where(Items_Table.category_id
                        .in(ids)
                )
                .async()
                .queryResultCallback(
                        new QueryTransaction.QueryResultCallback<Items>() {
                            @Override
                            public void onQueryResult(final QueryTransaction<Items> transaction, @NonNull final CursorResult<Items> tResult) {
                                sqliteCallBack.onDBDataListLoaded(
                                        new ArrayList<Items>(tResult.toListClose())
                                        , "getItemsWhereIdInIds");

                            }
                        }
                ).execute();

    }

    public void getCustomListCategoriesItemsJoin(final SqliteCallBack sqliteCallBack) {

        //synch

        List<CategoriesItemsQueryModel> categoriesItemsQueryModels = SQLite.select(
                Categories_Table.name.withTable().as("category_name"),
                Items_Table.name.withTable().as("item_name"),
                Categories_Table.id.withTable().as("category_id"),
                Items_Table.id.withTable().as("item_id"))
                .from(Categories.class)
                .leftOuterJoin(Items.class)
                .on(Categories_Table.id.withTable().eq(Items_Table.category_id.withTable()))
                .queryCustomList(CategoriesItemsQueryModel.class);

        sqliteCallBack.onDBDataListLoaded((ArrayList) categoriesItemsQueryModels
                , "getCustomListCategoriesItemsJoin");



    }


}
