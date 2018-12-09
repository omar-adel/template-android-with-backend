package com.example.template.model.bean.sqlite;

import android.content.Context;
import android.support.annotation.NonNull;

import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.dbFlowDatabases.DatabaseModule;

import modules.general.model.bean.sqlite.BaseModelWithIData;
import modules.general.utils.GeneralUtil;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.List;

import static modules.general.model.db.Constants.DBMethodGetAll;

/**
 * Created by Net22 on 11/19/2017.
 */

@Table(database = DatabaseModule.class)
public class Categories extends BaseModelWithIData {

    public Categories() {

    }

    public Categories(long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString() {

        String itemsArrayListStr = "";
        if (itemsArrayList != null) {
            itemsArrayListStr = GeneralUtil.convertArrToStr(itemsArrayList);
        }

        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemsArrayList=" + itemsArrayListStr +
                '}';

    }

    public String toStringAll() {

        String itemsArrayListStr = "";
        if (itemsArrayList != null) {
            itemsArrayListStr = GeneralUtil.convertArrToStr(itemsArrayList);
        } else {

        }

        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemsArrayList=" + itemsArrayListStr +
                '}';
//
//        return "Categories{" +
//                "id=" + id +
//                ", name='" + name +
//                '}';
    }

    public String toStringWithoutItems() {

        return "Categories{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }

    @Column
    @PrimaryKey(autoincrement = true)
    public long id = 0l;
    ;

    @Column
    public String name;

    public List<Items> itemsArrayList;


    public Categories(long id, String name, List<Items> itemsArrayList) {
        this.id = id;
        this.name = name;
        this.itemsArrayList = itemsArrayList;
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

    public List<Items> getItemsArrayList() {
        return itemsArrayList;
    }

    public void setItemsArrayList(List<Items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }


    @OneToMany(methods = OneToMany.Method.ALL, variableName = "itemsArrayList")
    public List<Items> dbFlowOneTwoManyUtilMethod() {
        if (itemsArrayList == null) {
            itemsArrayList = SQLite.select()
                    .from(Items.class)
                    .where(Items_Table.category_id.eq(id))
                    .queryList();
        }
        return itemsArrayList;
    }

    @Override
    public void insertData(Object object   , SqliteCallBack sqliteCallBack) {
        ((Categories)object).insert();
         sqliteCallBack.onDBDataObjectLoaded(null, "insertCategory");
    }

    @Override
    public void updateData(Object object , SqliteCallBack sqliteCallBack) {
        ((Categories)object).save();
         sqliteCallBack.onDBDataObjectLoaded(null, "updateCategory");

    }

    @Override
    public void deleteData(  Object object , SqliteCallBack sqliteCallBack) {
        ((Categories)object).delete();
         sqliteCallBack.onDBDataObjectLoaded(null, "deleteCategory");

    }

    @Override
    public Categories getItemByID(  int id ) {
        Categories category = SQLite.select()
                .from(Categories.class)
                .where(Categories_Table.id.eq(Long.valueOf(id)))
                .querySingle();

        return category;
    }

    @Override
    public void getAll(   final SqliteCallBack sqliteCallBack) {

        SQLite.select().from(Categories.class)
                .async()
                .queryResultCallback(
                        new QueryTransaction.QueryResultCallback<Categories>() {
                            @Override
                            public void onQueryResult(final QueryTransaction<Categories> transaction, @NonNull final CursorResult<Categories> tResult) {
                                sqliteCallBack.onDBDataListLoaded(
                                        new ArrayList<Categories>(tResult.toListClose())
                                        , "getAllCategories"
                                );
                            }
                        }
                ).execute();
    }
}
