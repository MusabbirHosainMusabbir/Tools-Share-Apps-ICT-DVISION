package com.tool.Utils;

import com.tool.toolsshare.model.ProductDetailsModel;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("registration.json")
    Call<ResponseBody> doRegistration(@Body RequestBody jsonObject);

    @POST("login.json")
    Call<ResponseBody> doLogin(@Body RequestBody jsonObject);

    @POST("create_post.json")
    Call<ResponseBody> publishPost(@Body RequestBody jsonObject);

    @POST("categorylist.json")
    Call<ResponseBody> getCategoryList(@Body RequestBody jsonObject);

    @POST("post_list.json")
    Call<ResponseBody> getPostList(@Body RequestBody jsonObject);

    @POST("post_detials.json")
    Call<ProductDetailsModel> getPostDetails(@Body RequestBody jsonObject);

    @POST("create_request.json")
    Call<ResponseBody> doRequest(@Body RequestBody jsonObject);

    @POST("request_list.json")
    Call<ResponseBody> getCount(@Body RequestBody jsonObject);

    @POST("user_details.json")
    Call<ResponseBody> getRequester(@Body RequestBody jsonObject);

    @POST("profile_update.json")
    Call<ResponseBody> updateProfile(@Body RequestBody jsonObject);

    @POST("request_update.json")
    Call<ResponseBody> acceptOrReject(@Body RequestBody jsonObject);

    @POST("forgot_password.json")
    Call<ResponseBody> forgetpassword(@Body RequestBody jsonObject);



}
