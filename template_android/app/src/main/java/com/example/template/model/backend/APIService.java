package com.example.template.model.backend;


import com.example.template.model.bean.CategoriesRestApiResponse;
import com.example.template.model.bean.CategoryRestApiResponse;
import com.example.template.model.bean.EditRestApiResponse;
import com.example.template.model.bean.ItemRestApiResponse;
import com.example.template.model.bean.ItemsRestApiResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Chike on 12/3/2016.
 */

public interface APIService {


    //REST API
    //CATEGORIES

    @POST("getAllCategoriesWithoutItems.php")
    @FormUrlEncoded
    Call<CategoriesRestApiResponse> getCategories(@Field("securityKey") String securityKey);

    @POST("getAllCategoriesWithoutItems.php")
    @FormUrlEncoded
    Observable<CategoriesRestApiResponse> getCategoriesRX(@Field("securityKey") String securityKey);

    @POST("getCategoryById.php")
    @FormUrlEncoded
    Call<CategoryRestApiResponse> getCategoryById(@Field("category_id") long category_id);

    @POST("addCategory.php")
    @FormUrlEncoded
    Call<EditRestApiResponse> addCategory(
            @Field("name") String name
    );

    @POST("editCategory.php")
    @FormUrlEncoded
    Call<EditRestApiResponse> editCategory(
            @Field("name") String name, @Field("category_id") long category_id
    );

    @POST("deleteCategory.php")
    @FormUrlEncoded
    Call<EditRestApiResponse> deleteCategory(
            @Field("category_id") long category_id
    );

    //ITEMS


    @POST("getAllItemsOrderedByCategory.php")
    @FormUrlEncoded
    Call<ItemsRestApiResponse> getItems(@Field("securityKey") String securityKey);

    @POST("getAllItemsOrderedByCategory.php")
    @FormUrlEncoded
    Observable<ItemsRestApiResponse> getItemsRX(@Field("securityKey") String securityKey);


    @POST("getAllItemsByCategory.php")
    @FormUrlEncoded
    Call<ItemsRestApiResponse> getItemsByCategoryId(@Field("category_id") long category_id);


    @POST("getItemById.php")
    @FormUrlEncoded
    Call<ItemRestApiResponse> getItemById(@Field("item_id") long item_id);

    @POST("addItem.php")
    @FormUrlEncoded
    Call<EditRestApiResponse> addItem(
            @Field("name") String name, @Field("description") String description
            , @Field("category_id") long category_id
    );

    @POST("editItem.php")
    @FormUrlEncoded
    Call<EditRestApiResponse> editItem(@Field("item_id") long item_id,
                                       @Field("name") String name, @Field("description") String description,
                                       @Field("category_id") long category_id
    );

    @POST("deleteItem.php")
    @FormUrlEncoded
    Call<EditRestApiResponse> deleteItem(
            @Field("item_id") long item_id
    );


    //////////

    //FIREBASE

    //PHP
    @POST("sendToken.php")

    @FormUrlEncoded
    Call<ResponseBody> updateToken(
            @Field("registeration_id") String token
    );

  

}
