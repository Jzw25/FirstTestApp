package com.example.myapplication.application;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 修復WebView的多進程加載的bug
        initWebView();
    }

    /**
     * 在 Android 9 中，为改善应用稳定性和数据完整性，应用无法再让多个进程共享一个 WebView 数据目录。
     * 如果您的应用必须在多个进程中使用 WebView 实例，则您必须先使用 WebView.setDataDirectorySuffix()
     * 方法为每个进程指定唯一的数据目录后缀，然后再在相应进程中使用 WebView 的给定实例。该方法会将每个进程的
     * 网络数据放入应用数据目录内其自己的目录中。
     *在一个APP内部，存在多个进程A，B，C等都使用WebView的话，在9.0系统以上就会报错，不支持同时使用多个进程中具有相同数据目录的WebView。
     * 解决办法是为调用WebView的每个进程重新制定其唯一的数据目录。
     */
    private void initWebView() {
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.P){
            String processName = getProcessName();
            WebView.setDataDirectorySuffix(processName);
        }
    }
}
