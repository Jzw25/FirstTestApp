package com.example.myapplication.web;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class JsBridgeWebChromeClient extends WebChromeClient {
    public JsBridgeWebChromeClient() {
        super();
        JsBridge.regist("bridge", ShowToastPlugin.class);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        result.confirm(JsBridge.callJava(view,url));
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}
