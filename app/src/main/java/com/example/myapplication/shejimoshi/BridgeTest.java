package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 桥接模式
 * 1. 模式的结构
 * 桥接（Bridge）模式包含以下主要角色。
 * 抽象化（Abstraction）角色：定义抽象类，并包含一个对实现化对象的引用。
 * 扩展抽象化（Refined Abstraction）角色：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。
 * 实现化（Implementor）角色：定义实现化角色的接口，供扩展抽象化角色调用。
 * 具体实现化（Concrete Implementor）角色：给出实现化角色接口的具体实现。
 */
public class BridgeTest {

    public BridgeTest(){
        Implmentor implmentor = new ConcreteImpl();
        Abstraction abstraction = new MyAbstraction(implmentor);
        abstraction.doSomeThing();
    }

    public void secend(){
        //扩展了
        Implmentor implmentor = new ConcreteImpl2();
        SecendImpl secend = new MySecendImpl();
        Abstraction abstraction = new MySecendAbstraction(implmentor,secend);
        abstraction.doSomeThing();
    }

    //角色，部分
    interface Implmentor{
        void show();
    }

    //具体实现角色1
    public static class ConcreteImpl implements Implmentor{
        @Override
        public void show() {
            Log.d("BridgeTest", "show: "+"ConcreteImpl");
        }
    }

    //具体实现角色2
    public static class ConcreteImpl2 implements Implmentor{

        @Override
        public void show() {
            Log.d("BridgeTest", "show: "+"ConcreteImpl2");
        }
    }

    //抽象化角色
    public static abstract class Abstraction{
        protected Implmentor implmentor;

        public Abstraction(Implmentor implmentor) {
            this.implmentor = implmentor;
        }

        abstract void doSomeThing();
    }

    //扩展实现类
    public static class MyAbstraction extends Abstraction{

        public MyAbstraction(Implmentor implmentor) {
            super(implmentor);
        }

        @Override
        void doSomeThing() {
            //增加扩展
            Log.d("BridgeTest", "doSomeThing: "+"MyAbstraction kuozhan");
            implmentor.show();
        }
    }

    //第二个抽象角色
    interface SecendImpl{
        void secendShow();
    }
    //第二个具体实现抽象角色
    public class MySecendImpl implements SecendImpl{

        @Override
        public void secendShow() {
            Log.d("BridgeTest", "secendShow: "+"secendShow");
        }
    }

    //扩展类二
    public static class MySecendAbstraction extends Abstraction{
        private SecendImpl secend;

        public MySecendAbstraction(Implmentor implmentor,SecendImpl secend) {
            super(implmentor);
            this.secend = secend;
        }

        @Override
        void doSomeThing() {
            secend.secendShow();
        }
    }

}
