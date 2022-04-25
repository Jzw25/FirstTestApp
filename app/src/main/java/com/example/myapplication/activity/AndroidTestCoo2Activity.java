package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class AndroidTestCoo2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_test_coo2);
    }

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
}