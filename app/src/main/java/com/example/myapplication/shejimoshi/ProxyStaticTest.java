package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 静态代理模式
 * 1. 模式的结构
 * 代理模式的主要角色如下。
 * 抽象主题（Subject）类：通过接口或抽象类声明真实主题和代理对象实现的业务方法。
 * 真实主题（Real Subject）类：实现了抽象主题中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象。
 * 代理（Proxy）类：提供了与真实主题相同的接口，其内部含有对真实主题的引用，它可以访问、控制或扩展真实主题的功能。
 */
public class ProxyStaticTest {

    interface BaseSubject{
        void Resquest();
    }

    //真实主题
    public static class RealSubject implements BaseSubject{

        @Override
        public void Resquest() {
            Log.d("ProxyStaticTest", "Resquest: "+"RealSubject is Resquest");
        }
    }

    //代理主题
    public static class ProxySubject implements BaseSubject{
        private RealSubject realSubject;

        @Override
        public void Resquest() {
            realSubject = new RealSubject();
            before();
            realSubject.Resquest();
            after();
        }

        //操作前增加的操作
        private void before(){
            Log.d("ProxyStaticTest", "before: "+"do befrore");
        }
        //操作后增加的操作
        private void after(){
            Log.d("ProxyStaticTest", "after: "+"do after");
        }
    }


}
