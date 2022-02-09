package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 工厂模式
 * 工厂方法模式由抽象工厂、具体工厂、抽象产品和具体产品等4个要素构成
 * 1.抽象工厂（Abstract Factory）：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法 来创建产品。
 * 2.具体工厂（ConcreteFactory）：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。
 * 3.抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能。
 * 4.具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应。
 */
public class FactoryTest {

    public FactoryTest(){
        Andmon andmon = new House();
        FarmFactory farmFactory = new HouseFram();
        farmFactory.newAndmon();
        andmon.show();
    }

    public void show(Andmon andmon,FarmFactory farmFactory){
        farmFactory.newAndmon();
        andmon.show();
    }

    interface Andmon{
        void show();
    }

    public static class House implements Andmon{

        public House(){

        }
        @Override
        public void show() {
            Log.d("FactoryTest", "show: "+"create a House");
        }
    }

    public static class Fox implements Andmon{

        @Override
        public void show() {
            Log.d("FactoryTest", "show: "+"create a Fox");
        }
    }

    interface FarmFactory{
        Andmon newAndmon();
    }

    public static class HouseFram implements FarmFactory{
        @Override
        public Andmon newAndmon() {
            Log.d("FactoryTest", "show: "+"create a HouseFram");
            return new House();
        }
    }

    public static class FoxFarm implements FarmFactory{
        @Override
        public Andmon newAndmon() {
            Log.d("FactoryTest", "show: "+"create a FoxFarm");
            return new Fox();
        }
    }
}
