package com.example.myapplication.observer;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 实现LifecycleObserver接口，在activtiy中添加，即可在类中回调到activity生命周期，不用在activity生命周期回调
 * 中操作，自我类中即可管理，解耦
 */
public class LifecycleTestObserver implements LifecycleObserver {
    private static final String TAG = LifecycleTestObserver.class.toString();
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void testResum(){
        Log.d(TAG, "testResum: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void testStart(){
        Log.d(TAG, "testStart: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    private void testonAny(){
        Log.d(TAG, "testonAny: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void testonPurse(){
        Log.d(TAG, "testonPurse: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void testonCreate(){
        Log.d(TAG, "testonCreate: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void testOnDestroy(){
        Log.d(TAG, "testOnDestroy: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void testOnStop(){
        Log.d(TAG, "testOnStop: ");
    }
}
