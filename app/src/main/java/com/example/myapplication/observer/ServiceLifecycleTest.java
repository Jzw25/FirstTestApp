package com.example.myapplication.observer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class ServiceLifecycleTest implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void testOnCreate(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void testOnDestroy(){

    }
}
