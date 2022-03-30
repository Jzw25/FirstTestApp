package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityTestMvvmBinding;
import com.example.myapplication.model.TreeBean;
import com.example.myapplication.viewmodel.TestMvvmViewModel;

public class TestMvvmActivity extends AppCompatActivity {

    private ActivityTestMvvmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test_mvvm);
        TestMvvmViewModel testMvvmViewModel = new ViewModelProvider(this).get(TestMvvmViewModel.class);
        MutableLiveData<TreeBean> liveData = testMvvmViewModel.getLiveData();
        liveData.observe(this, new Observer<TreeBean>() {
            @Override
            public void onChanged(TreeBean treeBean) {
                binding.setBean(treeBean);
            }
        });
        testMvvmViewModel.getLiveData();
    }
}