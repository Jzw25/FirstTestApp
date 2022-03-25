package com.example.myapplication.observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;

/**
 * 观察网咯变化
 * 首先我们自定义一个 Class NetworkLiveData，继承 LiveData，重写它的 onActive 方法和 onInactive 方法
 * 在 onActive 方法中，我们注册监听网络变化的广播，即ConnectivityManager.CONNECTIVITY_ACTION。在 onInactive 方法的时候，我们注销广播。
 */
public class NetWorkLiveData extends LiveData<NetworkInfo> {
    private static final String TAG = NetWorkLiveData.class.toString();
    private Context mContext;
    private static NetWorkLiveData liveData;
    private NetworkReceiver receiver;
    private IntentFilter intentFilter;

    public NetWorkLiveData(Context mContext) {
        this.mContext = mContext;
        receiver = new NetworkReceiver();
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public static NetWorkLiveData getInstance(Context context){
        if(liveData==null){
            liveData = new NetWorkLiveData(context);
        }
        return liveData;
    }

    /**
     * .onActive()当LiveData对象具有活动观察者时调用该方法。
     * 注册
     */
    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive: ");
        mContext.registerReceiver(receiver,intentFilter);
    }

    /**
     * onInactive()当LiveData对象没有任何活动观察者时调用该方法
     * 注销
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive: ");
        mContext.unregisterReceiver(receiver);
    }

    /**
     * 网咯变化广播
     */
    private static class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            getInstance(context).setValue(activeNetwork);

        }
    }
}
