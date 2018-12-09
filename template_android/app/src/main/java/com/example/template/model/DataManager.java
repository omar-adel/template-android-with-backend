package com.example.template.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.example.template.model.backend.ApiHelper;
import modules.general.model.backend.listeners.IRestApiCallBack;
import modules.general.model.bean.sqlite.BaseModelWithIData;

import com.example.template.model.bean.CategoriesRestApiResponse;
import com.example.template.model.bean.ItemsRestApiResponse;
import com.example.template.model.bean.sqlite.CacheApi;
import com.example.template.model.db.DbHelper;
import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.listener.IDataHelper;
import modules.general.model.shareddata.Prefs;
import modules.general.utils.Constants;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static modules.general.utils.Constants.getAllCategoriesWithoutItems;
import static modules.general.utils.Constants.getAllItemsByCategory;
import static modules.general.utils.Constants.getAllItemsOrderedByCategory;
import static modules.general.utils.Constants.param_category_id;
import static modules.general.utils.Constants.param_category_name;
import static modules.general.utils.Constants.param_item_description;
import static modules.general.utils.Constants.param_item_id;
import static modules.general.utils.Constants.param_item_name;
import static modules.general.utils.Constants.param_securitykey;


/**
 * Created by Net5 on 2/25/2018.
 */

public class DataManager implements IDataHelper {
    String url;
    Map<String, String> params;

    private static DataManager INSTANCE = null;
    Context mContext;
     IRestApiCallBack<Object> mIRestApiCallBack;
    IRestApiCallBack<Object> mPresenterIRestApiCallBack;
    private ApiHelper mApiHelper;
    private DbHelper mDbHelper;

    public DbHelper getDbHelper() {
        return mDbHelper;
    }

    SqliteCallBack mSqliteCallBack;
    SqliteCallBack mPresenterSqliteCallBack;

    public static DataManager getInstance(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = new DataManager(context);
//        }
//        return INSTANCE;
        return new DataManager(context);
    }


    public DataManager(Context context) {
        mContext = context;
        mApiHelper = new ApiHelper(context,new ISaveData(){
            @Override
            public void onRequireSaveData(String url,Map<String, String> params,String result, String type
                    ,String objectOfArrayBeanName) {

                    CacheApi cacheApiDelete = new CacheApi();
                    HashMap hashMap = new HashMap<String, String>();
                    hashMap.put("url", url);
                    hashMap.put("beanName", type);
                    cacheApiDelete.setItemWithCustomData(hashMap);
                    deleteItemWithCustomData(cacheApiDelete);

                CacheApi cacheApi = new CacheApi();
                cacheApi.setUrl(url);
                cacheApi.setParams(params.toString());
                cacheApi.setResponse(result);
                cacheApi.setBeanName(type);
                cacheApi.setObjectOfArrayBeanName(objectOfArrayBeanName);
                Log.e("typetype",type);
                if(objectOfArrayBeanName!=null)
                {
                    Log.e("objectOfArrayBeanName",objectOfArrayBeanName);
                }
                cacheApi.setDate(new Date().getTime());
                insertData(cacheApi,mSqliteCallBack);
             }
        });
        mDbHelper = new DbHelper(context);
        setRestApiCallBack();
        setSqliteCallBack();
    }




    public interface ISaveData {
        void onRequireSaveData(String url, Map<String, String> params, String result, String type
                , String objectOfArrayBeanName);
    }

    private void setRestApiCallBack() {
        mIRestApiCallBack = new IRestApiCallBack<Object>() {
            @Override
            public void onDataListLoaded(ArrayList<Object> data, String url) {
                if(mPresenterIRestApiCallBack !=null)
                {
                    mPresenterIRestApiCallBack.onDataListLoaded(data, url);

                }
            }

            @Override
            public void onDataObjectLoaded(Object data, String url) {
                if(mPresenterIRestApiCallBack !=null)
                {
                    mPresenterIRestApiCallBack.onDataObjectLoaded(data, url);

                }
            }

            @Override
            public void onNetworkError(String message, String url) {
                if(mPresenterIRestApiCallBack !=null)
                {
                    mPresenterIRestApiCallBack.onNetworkError(message, url);
                }
            }

            @Override
            public void onNoInternet() {


                if(mPresenterIRestApiCallBack !=null) {
                    mPresenterIRestApiCallBack.onNoInternet();
                }
            }
        };
    }

    private void setSqliteCallBack() {

        mSqliteCallBack = new SqliteCallBack() {
            @Override
            public void onDBDataListLoaded(ArrayList data, String localDbOperation) {
                super.onDBDataListLoaded(data, localDbOperation);
                if(mPresenterSqliteCallBack!=null)
                {
                    mPresenterSqliteCallBack.onDBDataListLoaded(data,localDbOperation);

                }
            }

            @Override
            public void onDBDataObjectLoaded(Object data, String localDbOperation) {
                super.onDBDataObjectLoaded(data, localDbOperation);
                if(mPresenterSqliteCallBack!=null)
                {
                    mPresenterSqliteCallBack.onDBDataObjectLoaded(data,localDbOperation);
                }
            }

            @Override
            public void onDBDataListLoaded(ArrayList data) {
                super.onDBDataListLoaded(data);
                if(mPresenterSqliteCallBack!=null) {
                    mPresenterSqliteCallBack.onDBDataListLoaded(data);
                }
            }

            @Override
            public void onDBDataObjectLoaded(Object data) {
                super.onDBDataObjectLoaded(data);
                if(mPresenterSqliteCallBack!=null) {
                    mPresenterSqliteCallBack.onDBDataObjectLoaded(data);
                }
            }
        };
    }

    public void setPresenterRestApiCallBack(IRestApiCallBack<Object> callBack) {
        mPresenterIRestApiCallBack = callBack;
    }
    public void setPresenterSqliteCallBack(SqliteCallBack callBack) {
        mPresenterSqliteCallBack = callBack;
    }

    public void initData() {
        new Prefs.Builder()
                .setContext(mContext)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(mContext.getPackageName())
                .setUseDefaultSharedPreference(false)
                .build();

        FlowManager.init(FlowConfig.builder(mContext.getApplicationContext())
                .openDatabasesOnInit(true)
                .build());

    }



    public CacheApi loadCacheData(String url , Map params) {
         CacheApi cacheApiTemp=new CacheApi();
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("url", url);
        hashMap.put("params", params.toString());
        cacheApiTemp.setItemWithCustomData(hashMap);
        cacheApiTemp =  (CacheApi)getItemWithCustomData(cacheApiTemp);
        return cacheApiTemp;
    }


    public Object loadDataFromCache(String url , Map params) {
        String result = null;
        String beanName = null;
        CacheApi cacheApiTemp=new CacheApi();
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("url", url);
        hashMap.put("params", params.toString());
        cacheApiTemp.setItemWithCustomData(hashMap);
        CacheApi cacheApi =  (CacheApi)getItemWithCustomData(cacheApiTemp);
        if (cacheApi != null) {
            beanName = cacheApi.getBeanName();
            result = cacheApi.getResponse();
            try {
                Class type = Class.forName(beanName);
                Gson gson = new Gson();
                String objectOfArrayBeanName = cacheApi.getObjectOfArrayBeanName() ;
                Object ob=null;
                if(objectOfArrayBeanName==null)
                {
                    ob = (gson.fromJson(result, type));
                }
                else
                {
//                    if(objectOfArrayBeanName.equals(BeanObject.class.getName()))
//                    {
//                        Type listType = new TypeToken<ArrayList<BeanObject>>() {}.getType();
//                        ob = (gson.fromJson(result, listType));
//                    }
                }

                return ob;
            } catch (Exception ex) {
                Log.e("ExceptionCacheParseStr", ex.toString());
            }
        }

        return null;
    }


    public Object loadDataFromCache(CacheApi cacheApi) {
        String result = null;
        String beanName = null;

        if (cacheApi != null) {
            beanName = cacheApi.getBeanName();
            result = cacheApi.getResponse();
            try {
                Class type = Class.forName(beanName);
                Gson gson = new Gson();
                String objectOfArrayBeanName = cacheApi.getObjectOfArrayBeanName() ;
                Object ob=null;
                if(objectOfArrayBeanName==null)
                {
                    ob = (gson.fromJson(result, type));
                }
                else
                {
//                    if(objectOfArrayBeanName.equals(BeanObject.class.getName()))
//                    {
//                        Type listType = new TypeToken<ArrayList<BeanObject>>() {}.getType();
//                        ob = (gson.fromJson(result, listType));
//                    }
                }

                return ob;
            } catch (Exception ex) {
                Log.e("ExceptionCacheParseStr", ex.toString());
            }
        }

        return null;
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




    ///Backend  Rest  Api

    //Backend Categories and Items



    public void getRemoteCategories() {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = getAllCategoriesWithoutItems;
        params = new HashMap<>();
        params.put(param_securitykey, param_securitykey);
        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.getCategories(mIRestApiCallBack, true, url, params);
        } else {
            //Toast.makeText(mContext, mContext.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
             mIRestApiCallBack.onNoInternet();
            CacheApi cacheApi = loadCacheData(url, params);
            mSqliteCallBack.onDBDataObjectLoaded(cacheApi);
        }

    }

    public void getRemoteItems() {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = getAllItemsOrderedByCategory;
        params = new HashMap<>();
        params.put(param_securitykey, param_securitykey);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.getItems(mIRestApiCallBack, true, url, params);
        } else {
            //Toast.makeText(mContext, mContext.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
            mIRestApiCallBack.onNoInternet();
            CacheApi cacheApi = loadCacheData(url, params);
            mSqliteCallBack.onDBDataObjectLoaded(cacheApi);
        }

    }

    public void getRemoteItemsByCategoryId(long category_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = getAllItemsByCategory;
        params = new HashMap<>();
        params.put(param_category_id, "" + category_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.getItemsByCategory(category_id, mIRestApiCallBack, true, url, params);
        } else {
            //Toast.makeText(mContext, mContext.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
            mIRestApiCallBack.onNoInternet();
            CacheApi cacheApi = loadCacheData(url, params);
            mSqliteCallBack.onDBDataObjectLoaded(cacheApi);
        }

    }

    public void getRemoteCategoryById(long category_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.getCategoryById;
        params = new HashMap<>();
        params.put(param_category_id, "" + category_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.getCategoryById(category_id, mIRestApiCallBack, true, url, params);
        } else {

            //Toast.makeText(mContext, mContext.getString(R.string.noInternetConnection),
               //     Toast.LENGTH_LONG).show();
            mIRestApiCallBack.onNoInternet();
            CacheApi cacheApi = loadCacheData(url, params);
            mSqliteCallBack.onDBDataObjectLoaded(cacheApi);
         }

    }

    public void getRemoteItemById(long item_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.getItemById;
        params = new HashMap<>();
        params.put(param_item_id, "" + item_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.getItemById(item_id, mIRestApiCallBack, true, url, params);
        } else {
            //Toast.makeText(mContext, mContext.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
            mIRestApiCallBack.onNoInternet();
            CacheApi cacheApi = loadCacheData(url, params);
            mSqliteCallBack.onDBDataObjectLoaded(cacheApi);
        }

    }

    public void addRemoteCategory(String name) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.addCategory;
        params = new HashMap<>();
        params.put(param_category_name, "" + name);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.addCategory(name, mIRestApiCallBack, false, url, params);
        } else {
            mIRestApiCallBack.onNoInternet();
        }

    }


    public void editRemoteCategory(String name, long category_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.editCategory;
        params = new HashMap<>();
        params.put(param_category_name, "" + name);
        params.put(param_category_id, "" + category_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.editCategory(name, category_id, mIRestApiCallBack, false, url, params);
        } else {
            mIRestApiCallBack.onNoInternet();
        }

    }

    public void deleteRemoteCategory(long category_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.deleteCategory;
        params = new HashMap<>();
        params.put(param_category_id, "" + category_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.deleteCategoryById(category_id, mIRestApiCallBack, false, url, params);
        } else {
            mIRestApiCallBack.onNoInternet();
        }

    }


    public void addRemoteItem(String name, String description, long category_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.addItem;
        params = new HashMap<>();
        params.put(param_item_name, "" + name);
        params.put(param_item_description, "" + description);
        params.put(param_category_id, "" + category_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.addItem(name, description, category_id,
                    mIRestApiCallBack, false, url, params);
        } else {
            mIRestApiCallBack.onNoInternet();
        }

    }


    public void editRemoteItem(long item_id, String name, String description, long category_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.editItem;
        params = new HashMap<>();
        params.put(param_item_id, "" + item_id);
        params.put(param_item_name, "" + name);
        params.put(param_item_description, "" + description);
        params.put(param_category_id, "" + category_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.editItem(item_id, name, description, category_id, mIRestApiCallBack, false, url, params);
        } else {
            mIRestApiCallBack.onNoInternet();
        }

    }

    public void deleteRemoteItem(long item_id) {
        //check for connection if true go to apihelper and get model else check if these model cached so get it
        url = Constants.deleteItem;
        params = new HashMap<>();
        params.put(param_item_id, "" + item_id);

        if (ApiHelper.checkInternet(mContext)) {
            mApiHelper.deleteItemById(item_id, mIRestApiCallBack, false, url, params);
        } else {
            mIRestApiCallBack.onNoInternet();
        }

    }



}
