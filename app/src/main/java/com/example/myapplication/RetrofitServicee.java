package com.example.myapplication;

import org.json.JSONObject;

import okhttp3.RequestBody;
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
    Call<JSONObject> login(@Field("username") String name, @Field("password") String pwd);
}
