package com.example.myapplication.service;

import androidx.lifecycle.LifecycleService;

import com.example.myapplication.observer.ServiceLifecycleTest;

public class LifecycleTestService extends LifecycleService {
    public LifecycleTestService() {
        getLifecycle().addObserver(new ServiceLifecycleTest());
    }
}
