package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityBottomNavigationViewTestBinding;

public class BottomNavigationViewTestActivity extends AppCompatActivity {

    ActivityBottomNavigationViewTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavigationViewTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //设置默认选中item
        binding.bnv.getMenu().getItem(1).setChecked(true);

        /**
         * 设置选中菜单颜色
         * <selector xmlns:android="http://schemas.android.com/apk/res/android">
         *     <item android:color="@color/green" android:state_checked="true" />
         *     <item android:color="@color/white" android:state_checked="false" />
         * </selector>
         */
    }
    //动态添加菜单
    private void addMenu(){
        Menu menu = binding.bnv.getMenu();
        menu.add(0,0,0,"标题1");
        MenuItem item = menu.findItem(0);
        item.setIcon(R.mipmap.xigua);
    }

    //删除item
    private void deletMenu(){
        binding.bnv.getMenu().removeItem(R.id.item2);
    }
}