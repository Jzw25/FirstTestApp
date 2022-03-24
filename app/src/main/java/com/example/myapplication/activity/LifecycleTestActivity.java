package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.observer.LifecycleTestObserver;

/**
 * 从以上源码可知，SupportActivity已经替我们实现了被观察者应该实现的那一部分代码。因此，我们不需要再去实现这部分
 * 代码。当我们希望监听Activity的生命周期时，只需要实现观察者那一部分的代码，即让自定义组件实现LifecycleObserver接口即可。
 */
public class LifecycleTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_test);
        //添加观察者
        getLifecycle().addObserver(new LifecycleTestObserver());
    }
}