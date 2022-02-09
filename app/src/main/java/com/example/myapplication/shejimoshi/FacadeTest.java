package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 外观模式
 *1. 模式的结构
 * 外观（Facade）模式包含以下主要角色。
 * 外观（Facade）角色：为多个子系统对外提供一个共同的接口。
 * 子系统（Sub System）角色：实现系统的部分功能，客户可以通过外观角色访问它。
 * 客户（Client）角色：通过一个外观角色访问各个子系统的功能。
 */
public class FacadeTest {

    //普通外观模式
    public void facade(){
        Facade facade = new Facade();
        facade.methon();
    }

    //解决开闭原则的外观模式
    public void facadePer(){
        FacadePerfect facadePerfect = new FacadePerfectClass();
        facadePerfect.methon();
    }
    //解决开闭原则的外观模式
    public void facadePerSecond(){
        FacadePerfect facadePerfect = new FacadePerfectSecond();
        facadePerfect.methon();
    }

    public static final String TAG = FactoryTest.class.toString();

    public class Facade{
        FacadeOne facadeOne = new FacadeOne();
        FacadeTwo facadeTwo = new FacadeTwo();
        FacadeThree facadeThree = new FacadeThree();

        public void methon(){
            facadeOne.metond();
            facadeTwo.metond();
            facadeThree.metond();
        }
    }

    public class FacadeOne{
        public void metond(){
            Log.d(TAG, "metond: "+"this is FacadeOne");
        }
    }

    public class FacadeTwo{
        public void metond(){
            Log.d(TAG, "metond: "+"this is FacadeTwo");
        }
    }

    public class FacadeThree{
        public void metond(){
            Log.d(TAG, "metond: "+"this is FacadeThree");
        }
    }

    /**
     * 外观模式扩展,通过定义一个抽象类或者接口去实现开闭原则
     */

    public interface FacadePerfect{
        void methon();
    }

    public class FacadePerfectClass implements FacadePerfect{
        FacadeOne facadeOne = new FacadeOne();
        FacadeFour facadeFour = new FacadeFour();
        @Override
        public void methon() {
            facadeOne.metond();
            facadeFour.metond();
        }
    }

    public class FacadePerfectSecond implements FacadePerfect{
        FacadeOne facadeOne = new FacadeOne();
        FacadeTwo facadeTwo = new FacadeTwo();
        FacadeFour facadeFour = new FacadeFour();
        @Override
        public void methon() {
            facadeOne.metond();
            facadeTwo.metond();
            facadeFour.metond();
        }
    }

    public class FacadeFour{
        public void metond(){
            Log.d(TAG, "metond: "+"this is FacadeFour");
        }
    }
}
