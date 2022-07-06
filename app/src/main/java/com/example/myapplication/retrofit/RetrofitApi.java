package com.example.myapplication.retrofit;

import com.example.myapplication.TreeBean;
import com.example.myapplication.bean.SuccessBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET("project/tree/json")
    Call<ResponseBody> getList();

    /**
     * get 请求带参数，一般是拼接再url上如project/tree/json？name = xxx
     * 使用Query注解自动添加
     * @return
     */
    @GET("project/tree/json")
    Call<TreeBean> getList2(@Query("name")String name,@Query("pwd")String pwd);

    /**
     * get 请求带参数，一般是拼接再url上如project/tree/json？name = xxx
     *使用QueryMap注解自动添加，添加的是一个map类型对象
     * @param map
     * @return
     */
    @GET("project/tree/json")
    Call<TreeBean> getList3(@QueryMap Map<String,Object> map);

    /**
     * 可以不写url，使用@url注解添加地址
     * @param url
     * @return
     */
    @POST
    Call<TreeBean> getListUrl(@Url String url);

    /**
     * 使用body注解将请求体使用bean类型传入
     * @param successBean 请求体
     * @return
     */
    @POST("xxxx")
    Call<SuccessBean> getPostBody(@Body SuccessBean successBean);

    /**
     * retrofit 单文件上传，使用@Part注解，传入三种类型之一的MultipartBody.Part，并且使用@Multipart
     * @param part
     * @return
     */
    @Multipart
    @POST("xxxx")
    Call<SuccessBean> postFile(@Part MultipartBody.Part part);

    /**
     * retrofit 多文件上传，使用@Part注解，传入三种类型之一的其他类型，传入一个list，并且使用@Multipart
     * @param list 文件列表
     * @return
     */
    @Multipart
    @POST("xxxx")
    Call<SuccessBean> postFiles(@Part List<MultipartBody.Part> list);

    /**
     * 上传单文件，并且附带信息参数
     * @param part
     * @param map
     * @return
     */
    @Multipart
    @POST("xxxx")
    Call<SuccessBean> postFileAndPrama(@Part MultipartBody.Part part, @PartMap Map<String,Object> map);

    /**
     * 表单提交，使用@Field注解是单个参数形式添加，并且要添加@FormUrlEncoded注解使用
     * @param name
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("xxxx")
    Call<SuccessBean> postBiaoDan(@Field("name")String name,@Field("pwd") String pwd);

    /**
     * 表单提交，使用@FieldMap注解是多个参数集合形式添加，并且要添加@FormUrlEncoded注解使用
     * @param map 表单集合
     * @return
     */
    @FormUrlEncoded
    @POST("xxxx")
    Call<SuccessBean> postBiaoDans(@FieldMap Map<String,Object> map);

    /**
     * 下载文件，需要@Streaming注解
     * @return
     */
    @Streaming
    @GET("xxx")
    Call<ResponseBody> getFile();

    /**
     * 如何添加请求头部参数，有三个注解@Header，参数注解，传入，@HeaderMap参数注解，传入map
     * @Headers方法注解，传入string数组
     * @param version
     * @param map
     * @return
     */
    @Headers({"type:what","name:jzw","way:ad"})
    @POST("xxxx")
    Call<ResponseBody> testHear(@Header("version") String version, @HeaderMap Map<String,Object> map);
}
