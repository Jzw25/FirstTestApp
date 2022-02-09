package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 *
 * 动态代理具体步骤：
 * 通过实现 InvocationHandler 接口创建自己的调用处理器；
 * 通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
 * 通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
 * 通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
 *
 */
public class ProxyActivityTest {

    public ProxyActivityTest(){
        ProxyActivityInterface proxyActivityInterface = new OneProxyActivity();
        InvocationHandler handler = new ProxyHandler(proxyActivityInterface);
        ProxyActivityInterface inerface = (ProxyActivityInterface) Proxy.newProxyInstance(proxyActivityInterface.getClass().
                getClassLoader(),proxyActivityInterface.getClass().getInterfaces(),handler );
        inerface.show();
    }

    public static class ProxyHandler implements InvocationHandler{
        private Object object;

        public ProxyHandler(Object object){
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d("ProxyActivityTest", "beforeinvoke: "+method.getName());
            method.invoke(object,args);
            Log.d("ProxyActivityTest", "afterinvoke: "+method.getName());
            return null;
        }
    }

    interface ProxyActivityInterface{
        void show();
    }

    public static class OneProxyActivity implements ProxyActivityInterface{

        @Override
        public void show() {
            Log.d("ProxyActivityTest", "show: "+"show OneProxyActivity");
        }
    }
}
