package com.example.template.ui.TestRestApi.presenter.single;

import com.example.template.model.bean.CategoryRestApi;
import com.example.template.model.bean.CategoryRestApiResponse;
import com.example.template.model.bean.EditRestApiResponse;
import com.example.template.model.bean.ItemRestApi;
import com.example.template.model.bean.ItemRestApiResponse;

import java.util.ArrayList;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface IRestApiSingleFragmentContract {

    public interface IRestApiSingleFragmentView {
        void showProgress();

        void hideProgress();

        void showSpCategories(ArrayList arrayList);

        void showCategory(CategoryRestApiResponse categoryRestApiResponse);

        void showItem(ItemRestApiResponse itemRestApiResponse);

        void showEditRestApiResponse(EditRestApiResponse editRestApiResponse);
    }


    public interface IRestApiSingleFragmentPresenter extends Base.IPresenter {
        void loadElementById(String sourceFragment, long elementId);

        void loadCategories();

        void addCategory(CategoryRestApi categoryRestApi);

        void editCategory(CategoryRestApi categoryRestApi);

        void deleteCategory(CategoryRestApi categoryRestApi);

        void addItem(ItemRestApi itemRestApi);

        void editItem(ItemRestApi itemRestApi);

        void deleteItem(ItemRestApi itemRestApi);
    }
}
