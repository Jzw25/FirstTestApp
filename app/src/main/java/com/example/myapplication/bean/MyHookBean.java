package com.example.myapplication.bean;

import android.util.Log;

/**
 * 需要偷梁换柱的类
 * 定义自己的类，实现你想要的方法，用hook替换想要替换的类
 */
public class MyHookBean extends HookBean.HookPriClass {
    private ChangeLisenter lisenter;
    public MyHookBean(ChangeLisenter lisenter) {
        super();
        this.lisenter = lisenter;
    }

    @Override
    public void showName() {
        super.showName();
        lisenter.getName(getName());
    }

    public interface ChangeLisenter{
        void getName(String name);
    }

    public interface HookInterface{
        void show();
    }

    public static class MyHookOne implements HookInterface{

        @Override
        public void show() {
            Log.d(HookBean.TAG, "MyHookOne: this is MyHookOne show");
        }
    }

    public static class MyHookTwo implements HookInterface{

        @Override
        public void show() {
            Log.d(HookBean.TAG, "MyHookTwo: this is MyHookTwo show");
        }
    }

    public static class MyHookTest{
        private HookInterface hookInterface;

        public MyHookTest() {
            hookInterface = new MyHookOne();
        }

        public void show(){
            hookInterface.show();
        }

        public void doSomeThing(HookInterface my){
            my.show();
        }
    }

}
