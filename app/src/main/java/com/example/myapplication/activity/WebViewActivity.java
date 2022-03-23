package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.web.JavaScriptInterface;
import com.example.myapplication.databinding.ActivityWebViewBinding;
import com.example.myapplication.web.JsBridge;
import com.example.myapplication.web.JsBridgeWebChromeClient;
import com.example.myapplication.web.ShowToastPlugin;

import java.io.PipedReader;
import java.util.Set;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webView = binding.webview;
        initWebView();
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        webView.loadUrl("file:///android_asset/js.html");
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR2){
            //18，4.4以上才有用,两种方法对比在hdpi下Androiddiaoyongjs
            //通过WebView的evaluateJavascript（）
            webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        }else {
            //这是通过loadurl方式调用js代码
            // 通过Handler发送消息
            webView.post(new Runnable() {
                @Override
                public void run() {
                    // 注意调用的JS方法名要对应上
                    // 调用javascript的callJS()方法


                    webView.loadUrl("javascript:callJS()");

                    /**
                     * 特别注意：JS代码调用一定要在 onPageFinished（） 回调之后才能调用，否则不会调用。
                     *
                     * onPageFinished()属于WebViewClient类的方法，主要在页面加载结束时调用
                     */
                }
            });
        }

    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        // 设置与Js交互的权限
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                builder.setMessage(message);
                builder.setTitle("Alter");
                builder.setPositiveButton("确认", (dialog, which) -> result.confirm());
                builder.setCancelable(false);
                builder.create().show();
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    /**
     * JS通过WebView调用 Android 代码
     * 对于JS调用Android代码的方法有3种：
     *
     * 通过WebView的addJavascriptInterface（）进行对象映射
     * 通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
     * 通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息
     */
    @SuppressLint("JavascriptInterface")
    private void addLocalFunction(){
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        //AndroidtoJS类对象映射到js的test对象
        webView.addJavascriptInterface(new JavaScriptInterface(),"test");
        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        webView.loadUrl("file:///android_asset/jssj.html");
    }


    /**
     * 通过 WebViewClient 的方法shouldOverrideUrlLoading ()回调拦截 url
     * 具体原理：
     * Android通过 WebViewClient 的回调方法shouldOverrideUrlLoading ()拦截 url
     * 解析该 url 的协议
     * 如果检测到是预先约定好的协议，就调用相应方法
     */
    private void shouldUrl(){
        // 步骤1：加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        webView.loadUrl("file:///android_asset/shouldurl.html");

        // 复写WebViewClient类的shouldOverrideUrlLoading方法
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri url = request.getUrl();
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if(url.getScheme().equals("js")){
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if(url.getAuthority().equals("webview")){
                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        Set<String> parameterNames = url.getQueryParameterNames();
                        //如果JS想要得到Android方法的返回值，只能通过 WebView 的 loadUrl （）去执行 JS 方法把返回值传递回去
                        String result = "aaa";
                        webView.loadUrl("javascript:returnResult(" + result + ")");
                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    private void testJsBrigde(){
        webView.setWebChromeClient(new JsBridgeWebChromeClient());
        webView.loadUrl("file:///android_asset/index.html");



    }

}