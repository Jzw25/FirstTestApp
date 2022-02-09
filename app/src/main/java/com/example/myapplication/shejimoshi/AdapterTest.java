package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 适配器模式
 * 1. 模式的结构
 * 适配器模式（Adapter）包含以下主要角色。
 * 目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或接口。
 * 适配者（Adaptee）类：它是被访问和适配的现存组件库中的组件接口。
 * 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。
 */
public class AdapterTest {

    public AdapterTest(){
        Monior monior = new ElecterMonior();
        monior.driver();
    }

    //目标
    interface Monior{
        void driver();
    }

    public static class LightDriver{
            public void lDriver(){
            Log.d("AdapterTest", "lDriver: "+"LightDriver");
        }
    }

    public class LightMonior implements Monior{
        private LightDriver lightDriver;

        public LightMonior(){
            lightDriver = new LightDriver();
        }
        @Override
        public void driver() {
            lightDriver.lDriver();
        }
    }

    public static class ElecterDriver{
        public void eDriver(){
            Log.d("AdapterTest", "lDriver: "+"ElecterDriver");
        }
    }

    public static class ElecterMonior implements Monior{

        private ElecterDriver electerDriver;
        public ElecterMonior(){
            electerDriver = new ElecterDriver();
        }
        @Override
        public void driver() {
            electerDriver.eDriver();
        }
    }
}
