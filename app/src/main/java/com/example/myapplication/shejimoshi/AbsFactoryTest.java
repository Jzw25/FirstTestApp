package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 抽象工厂
 * 抽象工厂模式同工厂方法模式一样，也是由抽象工厂、具体工厂、抽象产品和具体产品等 4 个要素构成，但抽象工厂中方法个数不同，抽象产品的个数也不同。现在我们来分析其基本结构和实现方法。
 * 1.抽象工厂（Abstract Factory）：提供了创建产品的接口，它包含多个创建产品的方法 newProduct()，可以创建多个不同等级的产品。
 * 2.具体工厂（Concrete Factory）：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。
 * 3.抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品。
 * 4.具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间是多对一的关系。
 */
public class AbsFactoryTest {

    interface Adminal{
        void show();
    }

    public static class Brid implements Adminal{

        @Override
        public void show() {
            Log.d("AbsFactoryTest", "show: "+"creat a Brid");
        }
    }

    public static class Dog implements Adminal{
        @Override
        public void show() {
            Log.d("AbsFactoryTest", "show: "+"creat a Dog");
        }
    }

    interface Plant{
        void show();
    }

    public static class Fruitage implements Plant{
        @Override
        public void show() {
            Log.d("AbsFactoryTest", "show: "+"creat a Fruitage");
        }
    }

    public static class Vegtable implements Plant{

        @Override
        public void show() {
            Log.d("AbsFactoryTest", "show: "+"creat a Vegtable");
        }
    }

    //增加一类，所有工厂都要修改，改变一族只需增加一个工厂
    interface AbsFarmFactory{
        Adminal getAdminal();
        Plant getPlant();
    }

    public static class XTFarmFactory implements AbsFarmFactory{

        @Override
        public Adminal getAdminal() {
            return new Dog();
        }

        @Override
        public Plant getPlant() {
            return new Fruitage();
        }
    }
}
