package com.example.myapplication.web;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 看到该方法中使用了 new Callback(webView, port)进行新建对象，该对象就是用来回调js中回调方法的java对应的类。
 * 这个类你需要将js传来的port传进来之外，还需要将WebView的引用传进来，因为要使用到WebView的loadUrl方法，为了
 * 防止内存泄露，这里使用弱引用。如果你需要回调js的callback，在对应的方法里调用一下callback.apply()方法将返
 * 回数据传入即可
 */
public class CallBack {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static final String CALLBACK_JS_FORMAT = "javascript:JSBridge.onFinish('%s', %s);";
    private String mPort;
    private WeakReference<WebView> mWebViewRef;

    public CallBack(String mPort, WebView webView) {
        this.mPort = mPort;
        mWebViewRef = new WeakReference<>(webView);
    }

    public void apply(JSONObject jsonObject){
        final String execJs = String.format(CALLBACK_JS_FORMAT, mPort, String.valueOf(jsonObject));
        if (mWebViewRef != null && mWebViewRef.get() != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });

        }
    }
}
