package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAndroidTestView2Binding;
import com.google.android.material.snackbar.Snackbar;

public class AndroidTestView2Activity extends AppCompatActivity {

    ActivityAndroidTestView2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidTestView2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(binding.ll, "请查收信息", Snackbar.LENGTH_LONG);
            snackbar.setAction("查看",view -> {
               //todo
            });
            snackbar.show();
        });
    }
}