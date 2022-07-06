package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.dagger.Cat;

import javax.inject.Inject;

/**
 * 使用依赖的对象
 * 在 TestDaggerAndHiltActivity 中创建了一个 cat变量，并加上了 @Inject注解，来告诉 Dagger2 你要为cat赋值，
 * 即依赖注入。所以 MainActivity 就是依赖的需求方
 */
public class TestDaggerAndHiltActivity extends AppCompatActivity {
    @Inject
    private Cat cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dagger_and_hilt);
    }

    private void dagger(){
//        DaggerMainComponent.builder()
//                .build()
//                .inject(this);
    }
}