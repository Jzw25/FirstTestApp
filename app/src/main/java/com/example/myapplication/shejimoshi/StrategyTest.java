package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.HashMap;

/**
 * 策略模式
 * 1. 模式的结构
 * 策略模式的主要角色如下。
 * 抽象策略（Strategy）类：定义了一个公共接口，各种不同的算法以不同的方式实现这个接口，环境角色使用这个接口调用不同的算法，
 * 一般使用接口或抽象类实现。
 * 具体策略（Concrete Strategy）类：实现了抽象策略定义的接口，提供具体的算法实现。
 * 环境（Context）类：持有一个策略类的引用，最终给客户端调用。
 */
public class StrategyTest {

   public static final String TAG = StrategyTest.class.toString();

   public void tryTest(){
      StrategyContext context = new StrategyContext();
      Strategy strategy = new ConcreteStrategyOther();
      context.strategy = strategy;
      strategy.method();
   }

   public void tryMapTest(){
      StrategyContextFactory factory = new StrategyContextFactory();
      Strategy strategy = new ConcreteStrategyOther();
      factory.putOne("a",strategy);
      Strategy strategy1 = new ConcreteStrategy();
      factory.putOne("b",strategy1);
      factory.start("b");
   }

   public interface Strategy{
      void method();
   }

   public static class ConcreteStrategy implements Strategy{
      @Override
      public void method() {
         Log.d(TAG, "method: this is ConcreteStrategy method");
      }
   }

   public static class ConcreteStrategyOther implements Strategy{
      @Override
      public void method() {
         Log.d(TAG, "method: this is ConcreteStrategyOther method");
      }
   }

   public static class StrategyContext{
      private Strategy strategy;

      public Strategy getStrategy() {
         return strategy;
      }

      public void setStrategy(Strategy strategy) {
         this.strategy = strategy;
      }

      public void start(){
         strategy.method();
      }
   }

   /**
    * 在一个使用策略模式的系统中，当存在的策略很多时，客户端管理所有策略算法将变得很复杂，如果在环境类中使用策略工
    * 厂模式来管理这些策略类将大大减少客户端的工作复杂度
    */

   public static class StrategyContextFactory{
      private HashMap<String,Strategy> map;

      public StrategyContextFactory() {
         map = new HashMap<>();
      }

      public HashMap<String, Strategy> getMap() {
         return map;
      }

      public void setMap(HashMap<String, Strategy> map) {
         this.map = map;
      }

      public void putOne(String key,Strategy strategy){
         map.put(key,strategy);
      }

      public Strategy getOne(String key){
         return map.get(key);
      }

      public void start(String key){
         map.get(key).method();
      }
   }

}
