package com.example.myapplication;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitServicee {
    @GET("project/tree/json")
    Call<TreeBean> getList();

    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("username") String name, @Field("password") String pwd);
}
