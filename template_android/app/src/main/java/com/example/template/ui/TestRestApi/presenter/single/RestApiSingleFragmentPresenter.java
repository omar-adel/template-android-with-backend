package com.example.template.ui.TestRestApi.presenter.single;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import com.example.template.model.DataManager;
import com.example.template.model.bean.CategoriesRestApiResponse;
import com.example.template.model.bean.CategoryRestApi;
import com.example.template.model.bean.CategoryRestApiResponse;
import com.example.template.model.bean.EditRestApiResponse;
import com.example.template.model.bean.ItemRestApi;
import com.example.template.model.bean.ItemRestApiResponse;
import com.example.template.model.bean.sqlite.CacheApi;

import modules.general.model.backend.listeners.IRestApiCallBack;
import modules.general.model.db.SqliteCallBack;
import com.example.template.ui.TestRestApi.fragments.RestApiSingleFragment;

import java.util.ArrayList;

import static modules.general.utils.Constants.RestApiSourceCategories;
import static modules.general.utils.Constants.RestApiSourceItems;
import static modules.general.utils.Constants.addCategory;
import static modules.general.utils.Constants.addItem;
import static modules.general.utils.Constants.deleteCategory;
import static modules.general.utils.Constants.deleteItem;
import static modules.general.utils.Constants.editCategory;
import static modules.general.utils.Constants.editItem;
import static modules.general.utils.Constants.getAllCategoriesWithoutItems;
import static modules.general.utils.Constants.getCategoryById;
import static modules.general.utils.Constants.getItemById;

/**
 * Created by Net22 on 11/26/2017.
 */

public class RestApiSingleFragmentPresenter extends SqliteCallBack implements IRestApiSingleFragmentContract.IRestApiSingleFragmentPresenter
        , IRestApiCallBack {
    Context mContext;
    IRestApiSingleFragmentContract.IRestApiSingleFragmentView mView;
    private DataManager mDataManager;

    public RestApiSingleFragmentPresenter(Context context, IRestApiSingleFragmentContract.IRestApiSingleFragmentView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext);
        mDataManager.setPresenterRestApiCallBack(this);
        mDataManager.setPresenterSqliteCallBack(this);
    }


    @Override
    public void loadElementById(String sourceFragment, long elementId) {

        mView.showProgress();
        if (sourceFragment.equals(RestApiSourceCategories)) {
            mDataManager.getRemoteCategoryById(elementId);
        } else if (sourceFragment.equals(RestApiSourceItems)) {
            mDataManager.getRemoteItemById(elementId);
        }

    }

    @Override
    public void loadCategories() {
        mView.showProgress();
        mDataManager.getRemoteCategories();
    }

    @Override
    public void addCategory(CategoryRestApi categoryRestApi) {

        mView.showProgress();
        mDataManager.addRemoteCategory(categoryRestApi.getName());
    }

    @Override
    public void editCategory(CategoryRestApi categoryRestApi) {
        mView.showProgress();
        mDataManager.editRemoteCategory(categoryRestApi.getName(), categoryRestApi.getId());
    }

    @Override
    public void deleteCategory(CategoryRestApi categoryRestApi) {
        mView.showProgress();
        mDataManager.deleteRemoteCategory(categoryRestApi.getId());

    }

    @Override
    public void addItem(ItemRestApi itemRestApi) {
        mView.showProgress();
        mDataManager.addRemoteItem(itemRestApi.getName(), itemRestApi.getDescription(), itemRestApi.getCategory_id());

    }

    @Override
    public void editItem(ItemRestApi itemRestApi) {
        mView.showProgress();
        mDataManager.editRemoteItem(itemRestApi.getId(), itemRestApi.getName(), itemRestApi.getDescription(), itemRestApi.getCategory_id());
    }

    @Override
    public void deleteItem(ItemRestApi itemRestApi) {
        mView.showProgress();
        mDataManager.deleteRemoteItem(itemRestApi.getId());
    }

    @Override
    public void onDataListLoaded(ArrayList data, String url) {

    }

    @Override
    public void onDataObjectLoaded(Object data, String url) {

        mView.hideProgress();
        if (url.equals(getAllCategoriesWithoutItems)) {

            if (((RestApiSingleFragment) mView).getSourceFragment().equals(RestApiSourceItems)) {
                ((RestApiSingleFragment) mView).showSpCategories(((CategoriesRestApiResponse) data).getResults());
            }

        } else if (url.equals(getCategoryById)) {
            ((RestApiSingleFragment) mView).showCategory(((CategoryRestApiResponse) data));
        } else if (url.equals(getItemById)) {
            ((RestApiSingleFragment) mView).showItem(((ItemRestApiResponse) data));
        } else if (url.equals(addCategory)) {
            ((RestApiSingleFragment) mView).showEditRestApiResponse(((EditRestApiResponse) data));
        } else if (url.equals(editCategory)) {
            ((RestApiSingleFragment) mView).showEditRestApiResponse(((EditRestApiResponse) data));
        } else if (url.equals(deleteCategory)) {
            ((RestApiSingleFragment) mView).showEditRestApiResponse(((EditRestApiResponse) data));
            ((AppCompatActivity) (mContext)).finish();
        } else if (url.equals(addItem)) {
            ((RestApiSingleFragment) mView).showEditRestApiResponse(((EditRestApiResponse) data));
        } else if (url.equals(editItem)) {
            ((RestApiSingleFragment) mView).showEditRestApiResponse(((EditRestApiResponse) data));
        } else if (url.equals(deleteItem)) {
            ((RestApiSingleFragment) mView).showEditRestApiResponse(((EditRestApiResponse) data));
            ((AppCompatActivity) (mContext)).finish();
        }


    }

    @Override
    public void onNetworkError(String message, String url) {
        mView.hideProgress();
    }

    @Override
    public void onNoInternet() {

        mView.hideProgress();
        //Toast.makeText(mContext, mContext.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();

    }


    @Override
    public void onDBDataObjectLoaded(Object data  ) {

        mView.hideProgress();
            CacheApi cacheApi = ((CacheApi) data);

            if (cacheApi != null) {
                if (cacheApi.getUrl().equals(getAllCategoriesWithoutItems)) {
                    CategoriesRestApiResponse categoriesRestApiResponse =
                            (CategoriesRestApiResponse) mDataManager.loadDataFromCache(cacheApi);

                    if (categoriesRestApiResponse != null) {
                        ((RestApiSingleFragment) mView).showSpCategories(
                                categoriesRestApiResponse.getResults());
                    }

                } else if (cacheApi.getUrl().equals(getCategoryById)) {
                    CategoryRestApiResponse categoryRestApiResponse = (CategoryRestApiResponse)
                            mDataManager.loadDataFromCache(cacheApi);
                    if (categoryRestApiResponse != null) {
                        ((RestApiSingleFragment) mView).showCategory(
                                categoryRestApiResponse);
                    }
                } else if (cacheApi.getUrl().equals(getItemById)) {
                    ItemRestApiResponse itemRestApiResponse = (ItemRestApiResponse)
                            mDataManager.loadDataFromCache(cacheApi);
                    if (itemRestApiResponse != null) {
                        ((RestApiSingleFragment) mView).showItem(
                                itemRestApiResponse);
                    }
                }
            }

    }
}