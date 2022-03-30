package com.example.myapplication.respository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.TreeBean;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 数据请求层，不管是数据库还是网咯来源，统一处理
 */
public class TestRespository {
    public void getData(MutableLiveData<TreeBean> liveData){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitServicee retrofitServicee = retrofit.create(RetrofitServicee.class);
        retrofit2.Call<TreeBean> list = retrofitServicee.getList();
        list.enqueue(new retrofit2.Callback<TreeBean>() {
            @Override
            public void onResponse(retrofit2.Call<TreeBean> call, retrofit2.Response<TreeBean> response) {
                Log.d("data", "onResponse: "+response.body().toString());
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<TreeBean> call, Throwable t) {

            }
        });
    }
}
