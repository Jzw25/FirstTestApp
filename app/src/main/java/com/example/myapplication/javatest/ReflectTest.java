package com.example.myapplication.javatest;

import android.util.Log;

import com.example.myapplication.bean.ReflectBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射测试
 * https://blog.csdn.net/sjh66655/article/details/110467429?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-1-110467429.pc_agg_new_rank&utm_term=Java+apt+%E4%BD%BF%E7%94%A8&spm=1000.2123.3001.4430
 */
public class ReflectTest {

    public static final String TAG = ReflectTest.class.toString();

    /**
     * 通过反射获取类
     * 获取Class对象的三种方式
     * 1.1 Object ——> getClass();
     * 1.2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
     * 1.3 通过Class类的静态方法：forName（String className）(常用)
     */
    private void getType(){
        //1.1
        ReflectBean reflectBean = new ReflectBean("name",20);
        Class<? extends ReflectBean> aClass = reflectBean.getClass();
        //1.2
        Class<ReflectBean> reflectBeanClass = ReflectBean.class;
        //1.3
        try {
            Class<?> aClass1 = Class.forName("com.example.myapplication.bean.ReflectBean");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 在运行期间，一个类，只有一个Class对象产生。
         * 三种方式常用第三种，第一种对象都有了还要反射干什么。第二种需要导入类的包，依赖太强，不导包就抛编译
         * 错误。一般都第三种，一个字符串可以传入也可写在配置文件中等多种方法
         */
    }

    /**
     * 通过反射获取方法
     * 1.批量的：
     * 		public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
     * 		public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
     * 2.获取单个的：
     * 		public Method getMethod(String name,Class<?>... parameterTypes):
     * 					参数：
     * 						name : 方法名；
     * 						Class ... : 形参的Class类型对象
     * 		public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
     *
     * 	 调用方法：
     * 		Method --> public Object invoke(Object obj,Object... args):
     * 					参数说明：
     * 					obj : 要调用方法的对象；
     * 					args:调用方式时所传递的实参；
     */
    private void getMethd(){
        try {
            //先获取类对象
            Class<?> reflectBeanclass = Class.forName("com.example.myapplication.bean.ReflectBean");
            //获取所有公有方法
            Method[] methods = reflectBeanclass.getMethods();
            for (Method m : methods){
                Log.d(TAG, "methods: " + m);
            }
            //获取所有方法，包括私有的
            Method[] declaredMethods = reflectBeanclass.getDeclaredMethods();
            for (Method m : declaredMethods){
                Log.d(TAG, "declaredMethods: " + m);
            }
            //获取公有的setPub方法
            Method setPub = reflectBeanclass.getMethod("setPub");
            Log.d(TAG, "setPub: "+setPub);
            //调用该方法
           setPub.invoke(null, null);
           //获取私有方法
            Method setWhere = reflectBeanclass.getDeclaredMethod("setWhere", String.class);
            Log.d(TAG, "setWhere: "+setWhere);
            //解除私有限定
            setWhere.setAccessible(true);
            //调用方法，获取返回值
            Object o = reflectBeanclass.getConstructor().newInstance();
            Object xitang = setWhere.invoke(o, "xitang");
            Log.d(TAG, "setWhere: "+xitang);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取成员变量
     */
    private void getFiled(){

    }

    /**
     * 通过反射获取构造方法
     * 调用方法：
     * 1.获取构造方法：
     * 1).批量的方法：
     * public Constructor[] getConstructors()：所有"公有的"构造方法
     * public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)
     *
     * 2).获取单个的方法，并调用：
     * public Constructor getConstructor(Class… parameterTypes):获取单个的"公有的"构造方法：
     * public Constructor getDeclaredConstructor(Class… parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
     *
     * 调用构造方法：
     * Constructor–>newInstance(Object… initargs)
     *
     * 2、 newInstance是 Constructor类的方法（管理构造函数的类）
     * api的解释为：
     * newInstance(Object… initargs)
     * 使用此 Constructor 对象表示的构造方法来创建该构造方法的声明类的新实例，并用指定的初始化参数初始化该实例。
     * 它的返回值是T类型，所以newInstance是创建了一个构造方法的声明类的新实例对象。并为之调用
     */
    private void getGouZao(){

    }
}
