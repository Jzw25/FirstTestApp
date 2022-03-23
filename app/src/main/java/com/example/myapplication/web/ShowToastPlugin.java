package com.example.myapplication.web;

import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowToastPlugin implements IBrigde{
    public static void showToast(WebView webView, JSONObject jsonObject,CallBack callBack){
        String msg = jsonObject.optString("msg");
        Toast.makeText(webView.getContext(),msg,Toast.LENGTH_SHORT).show();
        if(callBack!=null){
            JSONObject object = new JSONObject();
            try {
                object.put("name","zs");
                object.put("age","11");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            callBack.apply(object);
        }

    }
}
