package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityDrawerLayoutTestBinding;

public class DrawerLayoutTestActivity extends AppCompatActivity {
    ActivityDrawerLayoutTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrawerLayoutTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(this::onclick1);
    }

    private void onclick1(View view) {
        boolean open = binding.drl.isOpen();
        if(open){
            //关闭侧边菜单
            binding.drl.closeDrawers();
        }else {
            //打开侧边菜单
            binding.drl.openDrawer(Gravity.LEFT);
        }
    }
}