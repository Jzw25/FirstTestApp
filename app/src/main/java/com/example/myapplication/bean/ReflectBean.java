package com.example.myapplication.bean;

import android.util.Log;

import com.example.myapplication.javatest.ReflectTest;

/**
 * 反射测试配合类
 */
public class ReflectBean {
    private String name;
    public int age;

    public ReflectBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private void setWhat(){

    }

    private String setWhere(String where){
        Log.d(ReflectTest.TAG, "setWhere: this is do");
        return where;
    }

    public void setPub(){
        Log.d(ReflectTest.TAG, "setPub: this is do");
    }
}
