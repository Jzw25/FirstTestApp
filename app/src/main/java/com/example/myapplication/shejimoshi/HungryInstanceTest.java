package com.example.myapplication.shejimoshi;

/**
 * 饿汉式单例
 * 饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以是线程安全的，可以直接用于多线程而不会出现问题
 */
public class HungryInstanceTest{
    private static final HungryInstanceTest instance = new HungryInstanceTest();

    private HungryInstanceTest() {

    }

    public HungryInstanceTest getInstance(){
        return instance;
    }
}
