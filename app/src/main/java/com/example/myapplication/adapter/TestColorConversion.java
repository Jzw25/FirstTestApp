package com.example.myapplication.adapter;

import android.graphics.drawable.ColorDrawable;

import androidx.databinding.BindingConversion;

/**
 * 类型转换
 */
public class TestColorConversion {
    @BindingConversion
    public static ColorDrawable colorToDrawable(int color){
        return new ColorDrawable(color);
    }
}
