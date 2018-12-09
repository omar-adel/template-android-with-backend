package com.example.template.model.bean.sqlite;

import android.content.Context;
import android.support.annotation.NonNull;

import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;

import modules.general.model.bean.sqlite.BaseModelWithIData;

import static modules.general.model.db.Constants.DBMethodGetAll;

@Table(database = DatabaseModule.class)
public class SimpleItem extends BaseModelWithIData {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id = 0l;

    @Column
    public String name;

    public static long getCount() {
        return new Select(Method.count()).from(SimpleItem.class).count();
    }

    @Override
    public void insertData(Object object  ) {
        ((SimpleItem)object).insert();
    }

    @Override
    public void insertData(Object object   , SqliteCallBack sqliteCallBack) {
        ((SimpleItem)object).insert();
    }

    @Override
    public void updateData(Object object , SqliteCallBack sqliteCallBack) {
        ((SimpleItem)object).save();

    }

    @Override
    public void deleteData(  Object object , SqliteCallBack sqliteCallBack) {
        ((SimpleItem)object).delete();

    }

    @Override
    public SimpleItem getItemByID(  int id ) {
        SimpleItem simpleItem = SQLite.select()
                .from(SimpleItem.class)
                .where(SimpleItem_Table.id.eq(Long.valueOf(id)))
                .querySingle();

        return simpleItem;
    }

    @Override
    public void getAll(   final SqliteCallBack sqliteCallBack) {

        SQLite.select().from(SimpleItem.class)
                .async()
                .queryResultCallback(
                        new QueryTransaction.QueryResultCallback<SimpleItem>() {
                            @Override
                            public void onQueryResult(final QueryTransaction<SimpleItem> transaction,
                                                      @NonNull final CursorResult<SimpleItem> tResult) {
                                sqliteCallBack.onDBDataListLoaded(
                                        new ArrayList<SimpleItem>(tResult.toListClose())
                                        , "getAllSimpleItem"
                                );
                            }
                        }
                ).execute();
    }


}
