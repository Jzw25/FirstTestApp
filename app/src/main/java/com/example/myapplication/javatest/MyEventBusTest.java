package com.example.myapplication.javatest;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.example.myapplication.bean.BusType;
import com.example.myapplication.bean.MyEventBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义实现EventBus
 */
public class MyEventBusTest {
   private static volatile MyEventBusTest instance;

   //存放一个类中带了注解的方法，key是类
   private Map<Object, List<Method>> map ;

   private Handler handler;

   private MyEventBusTest() {
        map = new HashMap<>();
        handler = new Handler();
    }

    public synchronized static MyEventBusTest getIstance(){
       if(instance==null){
           instance = new MyEventBusTest();
       }
       return instance;
   }

    /**
     * 注册方法
     * @param obj 类
     */
   public void regist(Object obj){
       //先判断是否注册过
       List<Method> methods = map.get(obj);
       if(methods==null||methods.size()==0){
           methods = getMetohds(obj);
           if(methods!=null&&methods.size()>0){
               map.put(obj,methods);
           }
       }
   }

    /**
     * 没注册过的，通过反射获取类中所有方法，查看方法是否添加注解，如果是，放入list
     * @param obj
     * @return
     */
    private List<Method> getMetohds(Object obj) {
       List<Method> list = new ArrayList<>();

        Class<?> classz = obj.getClass();

        while (classz!=null){
            Method[] declaredMethods = classz.getDeclaredMethods();
            for (Method method : declaredMethods){
                MyEventBus annotation = method.getAnnotation(MyEventBus.class);
                if(annotation!=null){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        if(method.getParameterCount()!=1){
                            throw new RuntimeException("参数必须为一");
                        }
                    }else {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if(parameterTypes.length!=1){
                            throw new RuntimeException("参数必须为一");
                        }
                    }
                    list.add(method);
                }
            }

            //简单的递归，去匹配父类方法中有无注解
            classz = classz.getSuperclass();
        }

        return list;
    }

    /**
     * 发送Eventbus
     * 遍历列表中的所有key的方法，匹配参数和发送参数相同的方法，执行
     * @param obj 类
     */
    public void post(Object obj){
        //key集合
        Set<Object> objects = map.keySet();
        Iterator<Object> iterator = objects.iterator();
        while (iterator.hasNext()){
            //key,即存放方法时的类
            Object next = iterator.next();
            List<Method> methods = map.get(next);
            Iterator<Method> iterator1 = methods.iterator();
            while (iterator1.hasNext()){
                Method method = iterator1.next();
                try {
                    //判断参数类型是否匹配
                    if(method.getParameterTypes()[0].isAssignableFrom(obj.getClass())){
                        //执行此方法
                        MyEventBus annotation = method.getAnnotation(MyEventBus.class);
                        BusType value = annotation.value();
                        method.setAccessible(true);
                        switch (value){
                            case ASYN:
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            method.invoke(next,obj);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                break;
                            case MAIN:
                                if(Looper.myLooper()==Looper.getMainLooper()){
                                    method.invoke(next,obj);
                                }else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                method.invoke(next,obj);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            } catch (InvocationTargetException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                break;
                            default:
                                break;
                        }

//                        method.invoke(null,obj);//静态方法传入null，因为与对象无关
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
