package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.HashMap;

/**
 * 享元模式
 * 1. 模式的结构
 * 享元模式的主要角色有如下。
 * 抽象享元角色（Flyweight）：是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，非享元的外部状态以参数的形式通过方法传入。
 * 具体享元（Concrete Flyweight）角色：实现抽象享元角色中所规定的接口。
 * 非享元（Unsharable Flyweight)角色：是不可以共享的外部状态，它以参数的形式注入具体享元的相关方法中。
 * 享元工厂（Flyweight Factory）角色：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。
 */
public class FlyweightTest {

   public static final String TAG = FlyweightTest.class.toString();

   public void tryTets(){
      FlyweightFactory flyweightFactory = new FlyweightFactory();
      ConcreteFlyweight a = flyweightFactory.getConcreteFlyweight("a");
      ConcreteFlyweight b = flyweightFactory.getConcreteFlyweight("b");
      ConcreteFlyweight c = flyweightFactory.getConcreteFlyweight("c");
      ConcreteFlyweight d = flyweightFactory.getConcreteFlyweight("d");
      ConcreteFlyweight e = flyweightFactory.getConcreteFlyweight("e");

      a.show(new UnsharableFlyWeight("第一次调用a"));
      b.show(new UnsharableFlyWeight("第二次调用a"));
      c.show(new UnsharableFlyWeight("第三次调用a"));
      d.show(new UnsharableFlyWeight("第一次调用b"));
      e.show(new UnsharableFlyWeight("第二次调用b"));
   }

   //非享元角色
   public static class UnsharableFlyWeight{
      private String info;

      public UnsharableFlyWeight(String info) {
         this.info = info;
      }

      public String getInfo() {
         return info;
      }

      public void setInfo(String info) {
         this.info = info;
      }
   }

   //抽象享元
   public interface Flyweight{
      void show(UnsharableFlyWeight unsharableFlyWeight);
   }

   //具体享元
   public static class ConcreteFlyweight implements Flyweight{
      private String key;

      public ConcreteFlyweight(String key){
         this.key = key;
      }

      @Override
      public void show(UnsharableFlyWeight unsharableFlyWeight) {
         Log.d(TAG, "show: "+key+"is check");
         Log.d(TAG, "show: unsharableFlyWeight is "+unsharableFlyWeight.info);
      }
   }

   public static class FlyweightFactory{
      private HashMap<String,ConcreteFlyweight> map = new HashMap<>();

      public ConcreteFlyweight getConcreteFlyweight(String key){
         ConcreteFlyweight concreteFlyweight = map.get(key);
         if(concreteFlyweight!=null){
            Log.d(TAG, "getConcreteFlyweight: "+key+"is haved");
            return concreteFlyweight;
         }else {
            concreteFlyweight = new ConcreteFlyweight(key);
            map.put(key,concreteFlyweight);
            return concreteFlyweight;
         }
      }
   }
}
