package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.bean.SuccessBean;
import com.example.myapplication.databinding.ActivityDataBindingTestBinding;

/**
 * 自动空指针检查
 * Data Binding 会对变量进行空判断，假如说未对给定的变量赋值的话，就会给予变量一个默认的值，比如：
 * { user.name } -> null
 * { user.age } -> 0
 *
 * include
 * Data Binding 支持 include 传递变量，如：
 *
 * <include layout = "@layout/name" bind:user = "@{user}"/>
 * 1
 * 但是 Data Binding 并不支持 direct child，例如引入的 layout 根标签为 merge
 */
public class DataBindingTestActivity extends AppCompatActivity {
    private ActivityDataBindingTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test);
        SuccessBean successBean = new SuccessBean();
        successBean.setId("123");
        successBean.setName("zzzzz");
        binding.setBean(successBean);
    }
}