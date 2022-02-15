package com.example.myapplication.bean;

import com.example.myapplication.javatest.MyAnnotationTest;

/**
 * 为实体类配置自定义注解
 */
@MyAnnotationTest(isChange = true,isCheck = false,value = "")
@MyAnnotationTest.AnDefault(id = 2)
public class AnnotationTestBean {
    @MyAnnotationTest.UseTest
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 如果注解的属性只有一个，且叫value，那么使用该注解时，可以不用指定属性名，因为默认就是给value赋值：
     */
    @MyAnnotationTest.MethodTest("had vaule")
    public void method(){

    }

    public static final int IDEA_METHOD = 0;

    public static final int ANDROID_METHOD = 1;

    public static final int KOTLIN_METHOD = 2;

    public void method(@MyAnnotationTest.MethodCheck int method){

    }

    public static final String MY_WAY = "MY_WAY";

    public static final String YOUR_WAY = "YOUR_WAY";

    public static final String HER_WAY = "HER_WAY";

    public void method(@MyAnnotationTest.CheckMyWayCheck String way){

    }

}
