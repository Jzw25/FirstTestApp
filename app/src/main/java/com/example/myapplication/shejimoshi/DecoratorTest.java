package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 装饰器模式
 * 1. 模式的结构
 * 装饰器模式主要包含以下角色。
 * 抽象构件（Component）角色：定义一个抽象接口以规范准备接收附加责任的对象。
 * 具体构件（ConcreteComponent）角色：实现抽象构件，通过装饰角色为其添加一些职责。
 * 抽象装饰（Decorator）角色：继承抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
 * 具体装饰（ConcreteDecorator）角色：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。
 */
public class DecoratorTest {

    public static final String TAG = DecoratorTest.class.toString();

    public DecoratorTest(){
        Monika monika = new NomolMonika();
        monika.show();
        Monika monika1 = new WhiteMonika(monika);
        monika1.show();
        Monika monika2 = new BlackMonika(monika);
        monika2.show();
    }


    public interface Monika{
        void show();
    }

    public class NomolMonika implements Monika{

        private String someThing;

        @Override
        public void show() {
            Log.d(TAG, "show: "+"this is NomolMonika"+someThing);
        }

        public void setSomeThing(String s){
            someThing = s;
        }
    }

    public class Changer implements Monika{
        public Monika monika;

        public Changer(Monika monika){
            this.monika = monika;
        }

        @Override
        public void show() {
            monika.show();
        }
    }

    public class WhiteMonika extends Changer{

        public WhiteMonika(Monika monika) {
            super(monika);
        }

        @Override
        public void show() {
            super.show();
            setChanger();
        }

        private void setChanger(){
            Log.d(TAG, "setChanger: "+"this is WhiteMonika");
        }
    }

    public class BlackMonika extends Changer{

        public BlackMonika(Monika monika) {
            super(monika);
        }

        @Override
        public void show() {
            setChanger();
            super.show();
        }

        private void setChanger(){
            ((NomolMonika)monika).setSomeThing("BlackMonika");
        }
    }

}
