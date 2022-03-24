package com.example.myapplication.observer;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * ProcessLifecycleOwner是针对整个应用程序的监听，与Activity数量无关，你有一个Activity或多个Activity，对ProcessLifecycleOwner来说是没有区别的。
 * Lifecycle.Event.ON_CREATE只会被调用一次，而Lifecycle.Event.ON_DESTROY永远不会被调用。
 * 当应用程序从后台回到前台，或者应用程序被首次打开时，会依次调用Lifecycle.Event.ON_START和Lifecycle.Event.ON_RESUME。
 * 当应用程序从前台退到后台（用户按下Home键或任务菜单键），会依次调用Lifecycle.Event.ON_PAUSE和Lifecycle.Event.ON_STOP。
 * 需要注意的是，这两个方法的调用会有一定的延后。这是因为系统需要为“屏幕旋转，由于配置发生变化而导致Activity重新创建”
 * 的情况预留一些时间。也就是说，系统需要保证当设备出现这种情况时，这两个事件不会被调用。因为当旋转屏幕时，你的应用程序并没有退到后台，它只是进入了横/竖屏模式而已。
 */
public class MyApplicationObserver implements LifecycleObserver {
    private static final String TAG = MyApplicationObserver.class.toString();
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate(){
        Log.d(TAG, "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart(){
        Log.d(TAG, "onStart: ");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume(){
        Log.d(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause(){
        Log.d(TAG, "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop(){
        Log.d(TAG, "onStop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy(){
        Log.d(TAG, "onDestroy: ");
    }
}
