package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.view.KtReflctClass;
import com.example.myapplication.view.OneUtils;

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
        //调用处
        OneUtils.showOne();
        OneUtils.showTwo();
        //jvmNaem调用处
        KtReflctClass ktReflctClass = new KtReflctClass();
        ktReflctClass.setAllAge(22);
        ktReflctClass.getValue();

    }
}