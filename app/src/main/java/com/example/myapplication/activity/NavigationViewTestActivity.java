package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityNavigationViewTestBinding;
import com.google.android.material.navigation.NavigationView;

public class NavigationViewTestActivity extends AppCompatActivity {

    ActivityNavigationViewTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationViewTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.nv.setItemIconTintList(null);
        //头部view点击事件
        View headerView = binding.nv.getHeaderView(0);
        headerView.setOnClickListener(v -> {

        });
        //item点击事件
        binding.nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return false;
            }
        });
    }
}