package com.example.myapplication.web;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;


/**
 * 该方法的实现其实很简单，从一个Map中查找key是不是存在，不存在则反射拿到对应的Class中的所有方法，将方法是public
 * static void 类型的，并且参数是三个参数，分别是Webview，JSONObject，Callback类型的，如果满足条件，
 * 则将所有满足条件的方法put进去，整个实现如下
 */
public class JsBridge {
    private static HashMap<String,HashMap<String, Method>> plugins = new HashMap<>();

    public static void regist(String name,Class<? extends IBrigde> classz){
        if(!plugins.containsKey(name)){
            plugins.put(name,getAllMethod(classz));
        }
    }

    private static HashMap<String, Method> getAllMethod(Class<? extends IBrigde> classz) {
        HashMap<String,Method> methodHashMap = new HashMap<>();
        Method[] methods = classz.getDeclaredMethods();
        for (Method method : methods){
            String name;
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (name = method.getName()) == null) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes!=null&&parameterTypes.length==3){
                if (parameterTypes[0] == WebView.class && parameterTypes[1] == JSONObject.class && parameterTypes[2] == CallBack.class) {
                    methodHashMap.put(name, method);
                }
            }
        }
        return methodHashMap;
    }

    /**
     * 而至于JSBridge类中的callJava方法，就是将js传来的uri进行解析，然后根据调用的类名别名从刚刚的map中查找是
     * 不是存在，存在的话拿到该类所有方法的methodMap，然后根据方法名从methodMap拿到方法，反射调用，并将参数传
     * 进去，参数就是前文说的满足条件的三个参数，即WebView，JSONObject，Callback。
     */

    //jsbridge://className:callbackAddress/methodName?jsonObj
    public static String  callJava(WebView webView, String url){
        String className = "";
        String param = "";
        String port = "";
        String methodName = "";
        if(!TextUtils.isEmpty(url)&&url.startsWith("JSBrigde")){
            Uri uri = Uri.parse(url);
             className = uri.getHost();
             param = uri.getQuery();
             port = uri.getPort()+"";
            String path = uri.getPath();
            if(!TextUtils.isEmpty(path)){
                 methodName = path.replace("/", "");
            }
        }

        if(plugins.containsKey(className)){
            HashMap<String, Method> methodHashMap = plugins.get(className);
            if(methodHashMap!=null&&methodHashMap.size()>0&&methodHashMap.containsKey(methodName)){
                Method method = methodHashMap.get(methodName);
                if(method!=null){
                    try {
                        method.invoke(null,webView,new JSONObject(param),new CallBack(port,webView));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
