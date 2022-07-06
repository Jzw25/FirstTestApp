package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.RetrofitServicee;
import com.example.myapplication.TreeBean;
import com.example.myapplication.bean.LoginBean;
import com.example.myapplication.bean.SuccessBean;
import com.example.myapplication.retrofit.RetrofitApi;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.ReferenceQueue;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Connection;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class HttpTestActivity extends AppCompatActivity {

    private static final String TAG = HttpTestActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
    }

    /**
     * javaapi get请求
     */
    private void javaapi(){
        try {
            //接口地址
            URL url = new URL("https:xxxxx");
            //请求类
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //请求方式
            connection.setRequestMethod("GET");
            //设置请求头
            connection.setRequestProperty("","");
            //设置请求超时时间
            connection.setConnectTimeout(5000);
            //建立连接
            connection.connect();
            //请求成功
            if(connection.getResponseCode()==200){
                //获取请求结果
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String s = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * javaapi post请求
     */
    private void testJavaApiPost(){
        try {
            //接口地址
            URL url = new URL("https:xxxxx");
            //请求类
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //请求方式
            connection.setRequestMethod("POST");
            //设置请求头
            connection.setRequestProperty("","");
            SuccessBean successBean = new SuccessBean();
            successBean.setId("123");
            successBean.setName("jzw");
            Gson gson = new Gson();
            String toJson = gson.toJson(successBean);
            byte[] bytes = toJson.getBytes();
            //设置请求内容长度
            connection.setRequestProperty("Content-Length",String.valueOf(bytes.length));
            OutputStream outputStream = connection.getOutputStream();
            //写入参数
            outputStream.write(bytes);
            //设置请求超时时间
            connection.setConnectTimeout(5000);
            //建立连接
            connection.connect();
            if(connection.getResponseCode()==200){
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                //得到请求结果
                String result = reader.readLine();
            }
        }catch (Exception e){

        }
    }

    /**
     * Javaapi 单文件上传
     * （一）、 HttpURLConnection上传文件操作步骤：
     * 1、实例化URL对象。调用URL有参构造方法，参数是一个url地址；
     * 2、调用URL对象的openConnection()方法，创建HttpURLConnection对象；
     * 3、调用HttpURLConnection对象setDoOutput(true)、setDoInput(true)、setRequestMethod(“POST”)；
     * 4、设置Http请求头信息；（Accept、Connection、Accept-Encoding、Cache-Control、Content-Type、User-Agent）
     * 5、调用HttpURLConnection对象的connect()方法，建立与服务器的真实连接；
     * 6、调用HttpURLConnection对象的getOutputStream()方法构建输出流对象；
     * 7、设置三个常用字符串常量：换行、前缀、分界线（NEWLINE、PREFIX、BOUNDARY）；
     * 8、获取表单中上传控件之外的控件数据，写入到输出流对象（根据HttpWatch提示的流信息拼凑字符串）；
     * 9、获取表单中上传控件的数据，写入到输出流对象（根据HttpWatch提示的流信息拼凑字符串）；
     * 10、调用HttpURLConnection对象的getInputStream()方法构建输入流对象；
     * 11、调用HttpURLConnection对象的getResponseCode()获取客户端与服务器端的连接状态码。如果是200，则执行以下操作，否则提示服务器连接异常；
     * 12、将输入流转成字节数组，返回给客户端。
     *
     * 多文件上传就是多添加几次文件
     */
    private void testUpLoadOne(){
        try {
            String boundary = "--------------adsdasdasda";
            //要上传的文件
            File file = new File("xxxx");
            URL url = new URL("xxxx");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            //设置请求头
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Cache-Control", "no-cache");
//            connection.setUseCaches(false);这个等于上面一行
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);//这个边界符，=的值是浏览器自动生成的
            //UA信息
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //建立连接
            connection.connect();
            //获取输入流
            OutputStream outputStream = connection.getOutputStream();
            //先空行并添加边界符
            StringBuffer sb = new StringBuffer();
            //适配windows 和 linxs
            sb.append("\r\n");
            sb.append(boundary);
            //添加参数
            sb.append("Content-Disposition: form-data; name = username");
            sb.append("\r\n");
            sb.append(boundary);
            sb.append("Content-Disposition: form-data; name =\"uploadFile\";filename=\""+file.getName()+"\"");
            sb.append("\r\n");
            sb.append("Content-Type:image.png");
            sb.append("\r\n");
            outputStream.write(sb.toString().getBytes());
            //将文件写入流
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] b = new byte[1024];
            int lin;
            while ((lin = bufferedInputStream.read(b,0,b.length))!=-1){
                outputStream.write(b,0,lin);
            }
            //写尾部文件
            sb = new StringBuffer();
            sb.append("\r\n");
            sb.append(boundary);
            outputStream.write(sb.toString().getBytes());

            //获取返回结果
            int responseCode = connection.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String result = bufferedReader.readLine();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     */
    private void getFile(){
        try {
            URL url = new URL("xxxx");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            //设置请求头
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Cache-Control", "no-cache");
//            connection.setUseCaches(false);这个等于上面一行
            //UA信息
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //建立连接
            connection.connect();
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                //先创建文件存储路径
                //获取文件路径
                File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                //判断是否存在文件
                if(!externalFilesDir.exists()){
                    //不存在就先创建
                    externalFilesDir.mkdirs();
                }
                File file = new File(externalFilesDir+File.separator+"aaa.png");
                //判断文件是否存在
                if(!file.exists()){
                    //创建文件
                    file.createNewFile();
                }
                //读取流
                InputStream inputStream = connection.getInputStream();
                //输出到文件中的流
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int line;
                while ((line=inputStream.read(bytes,0,bytes.length))!=-1){
                    fileOutputStream.write(bytes,0,line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * okhttp简单的get请求
     */
    private void testOkHttpGet(){
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url("xxxx")
                    .get()
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: " + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.code()==200){
                        Log.d(TAG, "onResponse: " + response.body().string());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * okhttp post请求
     */
    private void testOkHttpPost(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        SuccessBean successBean = new SuccessBean();
        successBean.setId("123");
        successBean.setName("jzw");
        Gson gson = new Gson();
        String toJson = gson.toJson(successBean);
        //设置请求体
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),toJson);
        Request request = new Request.Builder()
                .post(body)
                .url("xxxxx")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    Log.d(TAG, "onResponse: " + response.body().string());
                }
            }
        });
    }

    private Map<String,List<Cookie>> cookiesMap = new HashMap<>();
    /**
     * okhttp post请求 ,带请请求头，添加拦截器,缓存，cookies
     */
    private void testOkHttpPostHeard(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                /**
                 * 添加拦截器，可以拿到request对象，并调用newBuilder去创建一个一模一样的对象，并对其进行操作，这样
                 * 就可以全局的对所有okhttp对象添加公告属性请求头了，，当然这是前置操作，也可以进行后置操作
                 * 拦截器再还没有发送给服务器的时候会调用一次,可以添加多个拦截器，按照添加顺序执行，但是network的拦截器
                 * 会在最后执行
                 */
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //前置操作，通过chain对象获取request对象并且重新构建该对象，构建出来的是一模一样的对象，不会丢失之前的设置
                        Request.Builder builder = chain.request().newBuilder();
                        //添加前置操作，比如请求头
                        Request build = builder.addHeader("ua", "asdasd")
                                .addHeader("way", "ad")
                                .addHeader("gogogoo", "yeyeye")
                                .build();
                        //构建Response对象，返回
                        Response response = chain.proceed(build);
                        return response;
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return null;
                    }
                })
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //在addInterceptor之后执行
                        return null;
                    }
                })
                /**
                 * 开启缓存，默认是关闭的，第一个参数是存放缓存文件的地址，第二个是缓存大小，为1m，开启后okhttp会自动
                 * 启动缓存
                 */
                .cache(new Cache(new File("xxx"),1024*1024))
                /**
                 * 添加cookie缓存,
                 */
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        //得到cookie，缓存
                        cookiesMap.put(url.host(),cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        //注意，当设置了cookieJar就不能返回null，得返回一个空的list
                        List<Cookie> cookies = cookiesMap.get(url.host());
                        return cookies==null?new ArrayList<>():cookies;
                    }
                })
                .build();
        SuccessBean successBean = new SuccessBean();
        successBean.setId("123");
        successBean.setName("jzw");
        Gson gson = new Gson();
        String toJson = gson.toJson(successBean);
        //设置请求体
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),toJson);
        Request request = new Request.Builder()
                .post(body)
                //添加请求头
                .addHeader("version","1.0.1")
                .url("xxxxx")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    Log.d(TAG, "onResponse: " + response.body().string());
                }
            }
        });
    }

    /**
     * okhttp文件上传
     */
    private void testOkHttpUpLoad(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        //创建要上传的文件的请求体
        File file = new File("xxxx");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody body = new MultipartBody.Builder().addFormDataPart("file",file.getName(),requestBody).build();

        Request request = new Request.Builder().url("xxxx").post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * okhttp多文件上传
     */
    private void testOkHttpUpLoadMore(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        //创建要上传的文件的请求体
        File file = new File("xxxx");
        File file1 = new File("xxxx");
        File file2 = new File("xxxx");
        File file3 = new File("xxxx");

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/png"), file1);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/png"), file2);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/png"), file3);

        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("files",file.getName(),requestBody)
                .addFormDataPart("files",file1.getName(),requestBody1)
                .addFormDataPart("files",file2.getName(),requestBody2)
                .addFormDataPart("files",file3.getName(),requestBody3)
                .build();

        Request request = new Request.Builder().url("xxxx").post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Headers headers = response.headers();
                for (int i = 0 ; i <headers.size();i++){
                    Log.d(TAG, "onResponse: " + headers.name(i) + "===" + headers.value(i));
                }
            }
        });
    }

    //okhttp 文件下载
    private void testOkHttpDownload(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("xxxx")
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Headers headers = response.headers();
                String filename = headers.get("Contents-dispostion");
                //获取文件路径
                File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                if(!externalFilesDir.exists()){
                    externalFilesDir.mkdirs();
                }
                File file = new File(externalFilesDir + File.separator + filename);
                if(!file.exists()){
                    file.createNewFile();
                }
                //输入流
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                //从resoned获取数据输入流
                InputStream inputStream = response.body().byteStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes,0,bytes.length))!=-1){
                    fileOutputStream.write(bytes,0,len);
                }

            }
        });
    }

    /**
     * retrofit get 请求
     */
    private void testRetorfitGet(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/").build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofit2.Call<ResponseBody> call = retrofitApi.getList();
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: " + response.body().string());
                    //使用gson转换为json类型为bean类
                    Gson gson = new Gson();
                    TreeBean treeBean = gson.fromJson(response.body().string(), TreeBean.class);
                    Log.d(TAG, "onResponse: treebean : " + treeBean.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    /**
     * retrofit  请求  使用json转换器自动转化
     */
    private void testRetorfitGson(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitServicee retrofitServicee = retrofit.create(RetrofitServicee.class);
        retrofit2.Call<TreeBean> list = retrofitServicee.getList();
        list.enqueue(new retrofit2.Callback<TreeBean>() {
            @Override
            public void onResponse(retrofit2.Call<TreeBean> call, retrofit2.Response<TreeBean> response) {
                TreeBean bean = response.body();
                Log.d(TAG, "onResponse: "+bean.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<TreeBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit  get 请求  带参数
     */
    private void testRetorfitGetCanshu(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //输入参数
        retrofit2.Call<TreeBean> list = retrofitApi.getList2("jzw","123");
        list.enqueue(new retrofit2.Callback<TreeBean>() {
            @Override
            public void onResponse(retrofit2.Call<TreeBean> call, retrofit2.Response<TreeBean> response) {
                TreeBean bean = response.body();
                Log.d(TAG, "onResponse: "+bean.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<TreeBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit  get 请求  带参数 使用map
     */
    private void testRetorfitGetCanshuMap(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //输入参数
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","jzw");
        map.put("pwd",123456);
        retrofit2.Call<TreeBean> list3 = retrofitApi.getList3(map);
        list3.enqueue(new retrofit2.Callback<TreeBean>() {
            @Override
            public void onResponse(retrofit2.Call<TreeBean> call, retrofit2.Response<TreeBean> response) {
                TreeBean bean = response.body();
                Log.d(TAG, "onResponse: "+bean.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<TreeBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit   请求  带参数 地址
     */
    private void testRetorfitUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofit2.Call<TreeBean> listUrl = retrofitApi.getListUrl("xxxx");
        listUrl.enqueue(new retrofit2.Callback<TreeBean>() {
            @Override
            public void onResponse(retrofit2.Call<TreeBean> call, retrofit2.Response<TreeBean> response) {
                TreeBean bean = response.body();
                Log.d(TAG, "onResponse: "+bean.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<TreeBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit post body 请求
     */
    private void testRetorfitBody(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //实例化参数
        SuccessBean successBean = new SuccessBean();
        successBean.setName("jzw");
        successBean.setId("111");
        retrofit2.Call<SuccessBean> postBody = retrofitApi.getPostBody(successBean);
        postBody.enqueue(new retrofit2.Callback<SuccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<SuccessBean> call, retrofit2.Response<SuccessBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<SuccessBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit 单文件上传
     */
    private void testRetrofitUpLoad(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //和okhttp构建一样
        File file = new File("xxxx");
        RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),body);

        retrofit2.Call<SuccessBean> successBeanCall = retrofitApi.postFile(part);
        successBeanCall.enqueue(new retrofit2.Callback<SuccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<SuccessBean> call, retrofit2.Response<SuccessBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<SuccessBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit 多文件上传
     */
    private void testRetrofitUpLoads(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //和okhttp构建一样
        File file = new File("xxxx");
        RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),body);

        File file1 = new File("xxxx");
        RequestBody body1 = RequestBody.create(MediaType.parse("image/png"),file1);
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("file",file1.getName(),body1);

        File file2 = new File("xxxx");
        RequestBody body2 = RequestBody.create(MediaType.parse("image/png"),file2);
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("file",file2.getName(),body2);

        //构建文件列表
        List<MultipartBody.Part> list = new ArrayList<>();
        list.add(part);
        list.add(part1);
        list.add(part2);

        retrofit2.Call<SuccessBean> successBeanCall = retrofitApi.postFiles(list);
        successBeanCall.enqueue(new retrofit2.Callback<SuccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<SuccessBean> call, retrofit2.Response<SuccessBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<SuccessBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit 单文件上传,并附加信息
     */
    private void testRetrofitUpLoadAnds(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //和okhttp构建一样
        File file = new File("xxxx");
        RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),body);

        //构建参数
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","jzw");
        map.put("age",18);

        retrofit2.Call<SuccessBean> call = retrofitApi.postFileAndPrama(part, map);
        call.enqueue(new retrofit2.Callback<SuccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<SuccessBean> call, retrofit2.Response<SuccessBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<SuccessBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit post 表单提交
     */
    private void testRetroftBiaoDan(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofit2.Call<SuccessBean> call = retrofitApi.postBiaoDan("jzw", "123456");
        call.enqueue(new retrofit2.Callback<SuccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<SuccessBean> call, retrofit2.Response<SuccessBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<SuccessBean> call, Throwable t) {

            }
        });
    }

    /**
     * retrofit post 表单提交 map
     */
    private void testRetroftBiaoDanMap(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //参数
        Map<String,Object> map = new HashMap<>();
        map.put("name","jzw");
        map.put("age",18);
        map.put("pwd","123456");
        retrofit2.Call<SuccessBean> call = retrofitApi.postBiaoDans(map);
        call.enqueue(new retrofit2.Callback<SuccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<SuccessBean> call, retrofit2.Response<SuccessBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<SuccessBean> call, Throwable t) {

            }
        });

    }

    /**
     * retrofit 文件下载
     */
    private void getFileByRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofit2.Call<ResponseBody> call = retrofitApi.getFile();
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //保存文件
                try {
                    Headers headers = response.headers();
                    String filename = headers.get("Contents-dispostion");
                    //获取文件路径
                    File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    if(!externalFilesDir.exists()){
                        externalFilesDir.mkdirs();
                    }
                    File file = new File(externalFilesDir + File.separator + filename);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    //输入流
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    //从resoned获取数据输入流
                    InputStream inputStream = response.body().byteStream();
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = inputStream.read(bytes,0,bytes.length))!=-1){
                        fileOutputStream.write(bytes,0,len);
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * retorfit 带请求头
     */
    private void testRetrofitHHead(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加json转换器，当返回数据为json时，自动转换为bean类，当然还有其他转换器，xml等
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/")
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Map<String,Object> map = new HashMap<>();
        map.put("ua","sdasda");
        map.put("token","sdasdasd");
        retrofit2.Call<ResponseBody> call = retrofitApi.testHear("1.0.1", map);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void tryTest(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("xxxxx").addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofit2.Call<ResponseBody> list = retrofitApi.getList();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                //设置请求监听，什么时候开始dns解析等等的回调
                .eventListener(new EventListener() {
                    @Override
                    public void callStart(Call call) {
                        super.callStart(call);
                    }

                    @Override
                    public void dnsStart(Call call, String domainName) {
                        super.dnsStart(call, domainName);
                    }

                    @Override
                    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
                        super.dnsEnd(call, domainName, inetAddressList);
                    }

                    @Override
                    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
                        super.connectStart(call, inetSocketAddress, proxy);
                    }

                    @Override
                    public void secureConnectStart(Call call) {
                        super.secureConnectStart(call);
                    }

                    @Override
                    public void secureConnectEnd(Call call, @Nullable Handshake handshake) {
                        super.secureConnectEnd(call, handshake);
                    }

                    @Override
                    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol) {
                        super.connectEnd(call, inetSocketAddress, proxy, protocol);
                    }

                    @Override
                    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol, IOException ioe) {
                        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
                    }

                    @Override
                    public void connectionAcquired(Call call, Connection connection) {
                        super.connectionAcquired(call, connection);
                    }

                    @Override
                    public void connectionReleased(Call call, Connection connection) {
                        super.connectionReleased(call, connection);
                    }

                    @Override
                    public void requestHeadersStart(Call call) {
                        super.requestHeadersStart(call);
                    }

                    @Override
                    public void requestHeadersEnd(Call call, Request request) {
                        super.requestHeadersEnd(call, request);
                    }

                    @Override
                    public void requestBodyStart(Call call) {
                        super.requestBodyStart(call);
                    }

                    @Override
                    public void requestBodyEnd(Call call, long byteCount) {
                        super.requestBodyEnd(call, byteCount);
                    }

                    @Override
                    public void requestFailed(Call call, IOException ioe) {
                        super.requestFailed(call, ioe);
                    }

                    @Override
                    public void responseHeadersStart(Call call) {
                        super.responseHeadersStart(call);
                    }

                    @Override
                    public void responseHeadersEnd(Call call, Response response) {
                        super.responseHeadersEnd(call, response);
                    }

                    @Override
                    public void responseBodyStart(Call call) {
                        super.responseBodyStart(call);
                    }

                    @Override
                    public void responseBodyEnd(Call call, long byteCount) {
                        super.responseBodyEnd(call, byteCount);
                    }

                    @Override
                    public void responseFailed(Call call, IOException ioe) {
                        super.responseFailed(call, ioe);
                    }

                    @Override
                    public void callEnd(Call call) {
                        super.callEnd(call);
                    }

                    @Override
                    public void callFailed(Call call, IOException ioe) {
                        super.callFailed(call, ioe);
                    }
                })
                .build();
        SuccessBean successBean = new SuccessBean();
        successBean.setId("123");
        successBean.setName("jzw");
        Gson gson = new Gson();
        String toJson = gson.toJson(successBean);
        //设置请求体
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),toJson);
        Request request = new Request.Builder()
                .post(body)
                .url("xxxxx")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    Log.d(TAG, "onResponse: " + response.body().string());
                }
            }
        });
        /**
         * okhttp可以调用call.cancel立即停止一个请求，也可以通过OkHttpClient.cancel(tag)同时关闭多个请求okhttp
         * 可以调用call.cancel立即停止一个请求，也可以通过OkHttpClient.cancel(tag)同时关闭多个请求
         * 当你构建一请求时，使用RequestBuilder.tag(tag)来分配一个标签。之后你就可以用OkHttpClient.cancel(tag)
         * 来取消所有带有这个tag的call。
         */
        call.cancel();





    }

}