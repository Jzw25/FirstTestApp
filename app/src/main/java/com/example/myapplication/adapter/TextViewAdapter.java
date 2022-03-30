package com.example.myapplication.adapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

/**
 * 系统方法，settext
 */
public class TextViewAdapter {
    @BindingAdapter("android:text")
    public void setText(TextView tv,CharSequence text){
        //大写字母转成小写
        String s = text.toString().toLowerCase();
        tv.setText(s);
    }
}
