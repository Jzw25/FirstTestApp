package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.bean.SuccessBean;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.media.MediaActivity;
import com.example.myapplication.media.SoundPoolActivity;
import com.example.myapplication.observer.CustomObserver;
import com.example.myapplication.observer.LoginObserver;
import com.example.myapplication.shujujiegou.AnnularLinkedList;
import com.example.myapplication.shujujiegou.DoubleLinkedList;
import com.example.myapplication.shujujiegou.LinkedList;
import com.example.myapplication.shujujiegou.StringTask;
import com.example.myapplication.shujujiegou.TestTask;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button button,button2,button3,button4,button5,button6,btn_sound,btn_linkedlist,
            btn_annular,btn_task,btn_add,btn_nbl;

    private ImageView iv;

    private TextView tv;

    private Map<String,List<Cookie>> cookieList;//cookie
    private ActivityMainBinding mainBinding;

    private String path = "https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%8A%A0%E8%BD%BD%E4%B8%AD%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=696136924,102362054&os=3827820665,1437801368&simid=696136924,102362054&pn=2&rn=1&di=128260&ln=1567&fr=&fmq=1639211046737_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%253A%252F%252Fattach.bbs.miui.com%252Fforum%252F201804%252F17%252F102838j7mz7j3e67dep3hj.png%26refer%3Dhttp%253A%252F%252Fattach.bbs.miui.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1641803032%26t%3D9d7fa8af439db118a9a9fe7d0b12f69a&rpstart=0&rpnum=0&adpicid=0&nojc=undefined&dyTabStr=MCwzLDQsMSwyLDUsNiw3LDgsOQ%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        btn_sound = mainBinding.btnSound;
        btn_linkedlist = mainBinding.btnLinkedlist;
        btn_annular = mainBinding.btnAnnular;
        btn_task = mainBinding.btnTask;
        btn_add = mainBinding.btnAdd;
        btn_nbl = mainBinding.btnNbl;
        iv = findViewById(R.id.iv);

        btn_nbl.setOnClickListener(v -> {
            //中缀表达式转逆波兰表达式（后缀表达式）
            //思路两个栈，一个存放最终输出的，一个存放运算符，数字直接入输出栈，入栈运算符如果大于栈顶，或者栈顶是（，
            // 直接入栈，小于等于则弹出栈顶运算符入输出栈，再将运算符入运算符栈，如遇到）则弹出运算符栈内元素至（，除去两个（）
            //其余入输出栈，最后将运算符栈的出栈至输出栈，最后输出站的倒序数据为逆波兰结果(入栈时（）优先级最高)
            StringTask fhbTask = new StringTask(20);//符号栈
            StringTask resultTask = new StringTask(40);//结果栈
            String center = "1+((2+3)*4)-5";//中缀表达式
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i<center.length(); i++){
                int last = i+1;
                String count = center.substring(i,last);
                if("+".equals(count)||"-".equals(count)||"*".equals(count)||"/".equals(count)||
                        "(".equals(count)||")".equals(count)){
                    //为运算符
                    //先将数字入结果栈
                    if(sb.length()>0){
                        resultTask.add(sb.toString());
                        sb.setLength(0);
                    }
                    if(fhbTask.isEmpty()){
                        fhbTask.add(count);
                    }else {
                        String topYsf = fhbTask.getTop();
                        if("(".equals(count)||"(".equals(topYsf)){
                            fhbTask.add(count);
                        }else if(")".equals(count)){
                            topYsf = fhbTask.del();
                            while (!"(".equals(topYsf)){
                                resultTask.add(topYsf);
                                topYsf = fhbTask.del();
                            }
                        }else if(("+".equals(fhbTask)||"-".equals(fhbTask))&&("*".equals(count)||"/".equals(count))){
                            fhbTask.add(count);
                        }else {
                            resultTask.add(fhbTask.del());
                            fhbTask.add(count);
                        }
                    }
                }else {
                    //为数字
                    sb.append(count);
                }
            }

            resultTask.showAll();

        });

        btn_add.setOnClickListener(v -> {
            //计算器实现(使用两个栈，一个存数字，一个存运算符，当取到运算符先判断运算符栈中是否优先级小于等于，如果是，
            // 先取出两个数字于运算符进行运算，不是则入栈)
            String s = "7*2*2-5+1-5+3+4+4";
            StringTask stringTask = new StringTask(20);
            TestTask testTask = new TestTask(40);
            StringBuffer keepNum = new StringBuffer();
            int num;
            for (int i = 0;i<s.length();i++){
                num = i+1;
                String count = s.substring(i,num);
                if("+".equals(count)||"-".equals(count)||"/".equals(count)||"*".equals(count)){
                    //为运算符
                    if(stringTask.isEmpty()){
                        stringTask.add(count);
                    }else {
                        String ysf = stringTask.getTop();
                        //如果栈内运算符小于新入，直接入栈
                        if(("+".equals(ysf)||"-".equals(ysf))&&("/".equals(count)||"*".equals(count))){
                            stringTask.add(count);
                        }else {
                            //出栈运算，再加入栈
                            int num1 = testTask.pupOne();
                            int num2 = testTask.pupOne();
                            String fuhao = stringTask.del();
                            int result = 0;
                            if("*".equals(fuhao)){
                                result = num1*num2;
                            }else if("+".equals(fuhao)){
                                result = num1+num2;
                            }else if("-".equals(fuhao)){
                                result = num2-num1;
                            }else if("/".equals(fuhao)){
                                result = num2/num1;
                            }
                            testTask.add(result);
                            stringTask.add(count);
                        }
                    }
                    //为运算符时把数字压入数字栈，保证其百位十位可以检测到
                    if(keepNum.length()>0){
                        testTask.add(Integer.parseInt(keepNum.toString()));
                        keepNum.setLength(0);
                    }
                }else {
                    //为数字
                    keepNum.append(count);
                }
            }

            //统一出栈计算
            stringTask.showAll();
            testTask.showAll();
            while (!stringTask.isEmpty()){
                String fu = stringTask.del();
                int num1 = testTask.pupOne();
                int num2 = testTask.pupOne();
                int result = 0;
                if("*".equals(fu)){
                    result = num1*num2;
                }else if("+".equals(fu)){
                    result = num1+num2;
                }else if("-".equals(fu)){
                    result = num2-num1;
                }else if("/".equals(fu)){
                    result = num2/num1;
                }
                testTask.add(result);
            }
            testTask.showAll();
            Log.d(TestTask.TAG, "result: "+testTask.getTop());
        });

        btn_task.setOnClickListener(v -> {
            TestTask testTask = new TestTask(5);
            testTask.add(20);
            testTask.add(21);
            testTask.add(22);
            testTask.add(23);
            testTask.add(24);
            testTask.showAll();
            Log.d(TestTask.TAG, "getTop: "+testTask.getTop());
            Log.d(TestTask.TAG, "getSize: "+testTask.getSize());

        });

        btn_annular.setOnClickListener(v -> {
            AnnularLinkedList list = new AnnularLinkedList();
            list.add(10);
            list.add(11);
            list.add(12);
            list.add(13);
            list.add(14);
            list.showList();
            Log.d(AnnularLinkedList.TAG, "getSize: "+list.getSize());
            list.outOfList(2);
        });

        btn_linkedlist.setOnClickListener(v -> {
            DoubleLinkedList list = new DoubleLinkedList();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);

            list.showList();
            Log.d(DoubleLinkedList.TAG, "getSize: "+list.getSize());
        });

        btn_sound.setOnClickListener(v -> {
//                startActivity(new Intent(MainActivity.this, SoundPoolActivity.class));
            LinkedList list = new LinkedList();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);
            list.showList();
            Log.d(LinkedList.TAG, "onClick: "+list.getSize());
//                Log.d(LinkedList.TAG, "onClick: "+list.getBean(8).toString());
            list.reversalList();
            list.showList();
            list.useTask();
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MediaActivity.class));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObserver.login("jzw1","123456").subscribe(new CustomObserver() {
                    @Override
                    public void success(SuccessBean successBean) {
                        Log.d(TAG, "success: "+successBean.toString());
                    }

                    @Override
                    public void fail(String messagee) {
                        Log.d(TAG, "fail: "+messagee);
                    }
                });
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(MainActivity.this).load(path).
                        placeholder(getResources().getDrawable(R.mipmap.img))
                        .error(getResources().getDrawable(R.mipmap.img_1))
                        .into(iv);

//                //第二步
//                Observable.just(path)
//                        .map(new Function<String, Bitmap>() {
//                            @Override
//                            public Bitmap apply(String s) throws Throwable {
//                                //第三步
//                                URL url = new URL(s);
//                                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//                                urlConnection.setConnectTimeout(5000);
//                                if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
//                                    Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
//                                    return bitmap;
//                                }
//                                return null;
//                            }
//                        })
//                        //子线程
//                        .subscribeOn(Schedulers.io())
//                        //主线程
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<Bitmap>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//                                //订阅，第一步
//                            }
//
//                            @Override
//                            public void onNext(@NonNull Bitmap bitmap) {
//                                //第四步，接收数据
//                                iv.setImageBitmap(bitmap);
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                //第五步，结束
//
//                            }
//                        });
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://www.wanandroid.com/")
                        .build();
                RetrofitServicee retrofitServicee = retrofit.create(RetrofitServicee.class);
                retrofit2.Call<TreeBean> list = retrofitServicee.getList();
                list.enqueue(new retrofit2.Callback<TreeBean>() {
                    @Override
                    public void onResponse(retrofit2.Call<TreeBean> call, retrofit2.Response<TreeBean> response) {
                        Log.d(TAG, "onResponse: "+response.body().toString());
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TreeBean> call, Throwable t) {

                    }
                });
            }
        });

        tv = findViewById(R.id.tv);
        initRequest();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://www.wanandroid.com/").build();
                RetrofitServicee retrofitServicee = retrofit.create(RetrofitServicee.class);

                retrofit2.Call<JSONObject> login = retrofitServicee.login("jzw111", "123123");
                login.enqueue(new retrofit2.Callback<JSONObject>() {
                    @Override
                    public void onResponse(retrofit2.Call<JSONObject> call, retrofit2.Response<JSONObject> response) {
                        Log.d(TAG, "onResponse: "+response.body().toString());
                    }

                    @Override
                    public void onFailure(retrofit2.Call<JSONObject> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request.Builder().url("https://www.wanandroid.com/navi/json")
                        .build();
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request1 = chain.request().newBuilder().addHeader("os", "AD")
                                .addHeader("veersion", "7.0.0").build();
                        Response response1 = chain.proceed(request1);
                        return response1;
                    }
                }).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        Gson gson = new Gson();
//                        JSONObject jsonObject = gson.fromJson(response.body().string(),null);
                        Log.d(TAG, "onResponse: "+response.body().string());
                    }
                });
            }
        });
    }

    private void initRequest() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("os", "Android")
                        .addHeader("banben","30")
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        }).build();
//                cookieJar(new CookieJar() {
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                cookieList = new HashMap<>();
//                cookieList.put(url.host(),cookies);
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                return cookieList.get(url.host());
//            }
//        })


//        MediaType json = MediaType.get("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),"");
        FormBody formBody = new FormBody.Builder()
                .add("username","jzw111")
                .add("password","123123")
                .add("repassword","123123")
                .build();
        Request request = new Request.Builder().url("https://www.wanandroid.com/user/register")
                .post(formBody)
                .build();

        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: "+response.body().string());
                }
            }
        });

        //同步请求
//        try {
//            Response execute = client.newCall(request).execute();
//            execute.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}