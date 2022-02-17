package com.example.myapplication.javatest;

import android.util.Log;

import com.example.myapplication.bean.HookBean;
import com.example.myapplication.bean.MyHookBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Hook测试
 * Hook动态注入代码
 * Hook机制是回调机制的一种，普通的回调是静态的，我们必须提前写好回调接口；而Hook机制在Java中则可以利用反射，针对切入点(通常是一个成员变量)，采用替换的手段，使代码在运行时改变，听起来有些抽象，下面简单介绍下，然后我看代码。
 *
 * 寻找适合Hook点，它应该是一个成员变量，并且应该在我们需要注入的方法中调用过它的方法，或者使用了它的的值；
 * 创建继承自Hook点的对象的子类，根据需求修改其相应的方法；
 * 使用反射将我们自己创建的对象替换对象实例中的对象，达到偷梁换柱的目的。
 */
public class HookTets {
    public void tryTest(){
        HookBean bean = new HookBean();
        bean.show();
        try {
            Class<?> HookPriClass = Class.forName("com.example.myapplication.bean.HookBean$HookPriClass");
            Field name = HookPriClass.getDeclaredField("name");
            Object o = HookPriClass.getConstructor().newInstance();
            name.setAccessible(true);
            name.set(o,"lisi");
            Class<? extends HookBean> beanClass = bean.getClass();
            Field hookPriClass = beanClass.getDeclaredField("hookPriClass");
            hookPriClass.setAccessible(true);
            hookPriClass.set(bean,o);
            bean.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tryTets1(){
        HookBean bean = new HookBean();
        bean.show();
        try {
            Class<?> HookPriClass = Class.forName("com.example.myapplication.bean.HookBean$HookPriClass");
            Field name = HookPriClass.getDeclaredField("name");
            Object o = HookPriClass.getConstructor().newInstance();
            name.setAccessible(true);
            name.set(o,"lisi");
            Class<?> hookbean = Class.forName("com.example.myapplication.bean.HookBean");
            Object o1 = hookbean.getConstructor().newInstance();
            Field hookPriClass = hookbean.getDeclaredField("hookPriClass");
            hookPriClass.setAccessible(true);
            hookPriClass.set(o1,o);
            Method show = hookbean.getMethod("show");
            show.invoke(o1,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tryTest3(){
        HookBean bean = new HookBean();
        bean.show();
        try {
            Class<? extends HookBean> beanClass = bean.getClass();
            Field hookPriClass1 = beanClass.getDeclaredField("hookPriClass");
            hookPriClass1.setAccessible(true);
            Class<?> HookPriClass = Class.forName("com.example.myapplication.bean.HookBean$HookPriClass");
            Field name = HookPriClass.getDeclaredField("name");
            name.setAccessible(true);
            Object o = HookPriClass.getConstructor().newInstance();
            name.set(o,"wangwu");
            hookPriClass1.set(bean,o);
            bean.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 偷梁换柱
     */
    public void tryTest2(){
        HookBean bean = new HookBean();
        bean.show();
        try {
            Class<? extends HookBean> aClass = bean.getClass();
            Field hookPriClass = aClass.getDeclaredField("hookPriClass");
            hookPriClass.setAccessible(true);
            MyHookBean myHookBean = new MyHookBean(name -> Log.d(HookBean.TAG, "myHookBean: the name is " + name));
            hookPriClass.set(bean,myHookBean);
            bean.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 尝试破解防止机制
     * 成功
     * final修饰字段也可以反射修改
     */
    public void tryTest4(){
        HookBean bean = new HookBean();
        bean.show();
        try {
            Class<? extends HookBean> aClass = bean.getClass();
            Field hookPriClass = aClass.getDeclaredField("hookPriClass");
            hookPriClass.setAccessible(true);
            MyHookBean myHookBean = new MyHookBean(name -> Log.d(HookBean.TAG, "myHookBean: the name is " + name));
            hookPriClass.set(bean,myHookBean);
            Field id = aClass.getDeclaredField("id");
            id.setAccessible(true);
            id.set(bean,myHookBean.hashCode());
            bean.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态代理hook
     * 1、代理对象和被代理对象需要实现同样的接口，MyHookOne和MyHookTwo都是HookInterface的实现类。
     * 2、被代理对象必须在使用的时候，使用接口作为类型。否则不能被动态代理。  hookInterface.set(myHookTest,proxyInstance)会
     * 报错，类似设置错误。
     * 3、此案例中hook的对象是MyHookTest。一次修改后被系统回收后，hook就失效了。如果要长期生效，那么hook的生命
     * 周期应该是对应响应的生命周期，如应用全局的生命周期，如单例等。
     */
    public void tryProxyTest(){
        try {
            MyHookBean.MyHookTest myHookTest = new MyHookBean.MyHookTest();
            myHookTest.show();

            Class<? extends MyHookBean.MyHookTest> aClass = myHookTest.getClass();
            Field hookInterface = aClass.getDeclaredField("hookInterface");
            hookInterface.setAccessible(true);
            //需要替换的类，与原数据类都是实现相同接口
            MyHookBean.MyHookTwo myHookTwo = new MyHookBean.MyHookTwo();
            //配置代理
            Object proxyInstance = Proxy.newProxyInstance(MyHookBean.MyHookTwo.class.getClassLoader(), MyHookBean.MyHookTwo.class.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d(HookBean.TAG, "tryProxyTest: 代理了！");
                    /**
                     * todo 做你想做的事儿
                     */
                    method.invoke(myHookTwo,args);//如果是代理方法，讲调用方法，如果不写则拦截，不会执行原来调用
                    return null;
                }
            });

            //代理替换成员便量
            hookInterface.set(myHookTest,proxyInstance);
            myHookTest.show();

            //代理替换方法，如果不调用method.invoke(myHookTwo,args)则不会执行doSomeThing（）方法
            Method doSomeThing = aClass.getMethod("doSomeThing", MyHookBean.HookInterface.class);
            doSomeThing.invoke(myHookTest,proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
