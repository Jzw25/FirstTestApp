package com.example.myapplication.shejimoshi;

/**
 * 懒汉式单例
 * 只在获取时创建实例
 * (利用锁和volatile保证线程安全)
 */
public class InstanceTest {
    private volatile static InstanceTest instanceTest;

    private InstanceTest(){

    }
    public static synchronized InstanceTest getInstanceTest(){
        if(instanceTest==null){
            instanceTest = new InstanceTest();
        }
        return instanceTest;
    }
}
