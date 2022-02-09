package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器模式
 * 迭代器（Iterator）模式的定义：提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。迭代器模式是一种对象行为型模式，其主要优点如下。
 * 访问一个聚合对象的内容而无须暴露它的内部表示。
 * 遍历任务交由迭代器完成，这简化了聚合类。
 * 它支持以不同方式遍历一个聚合，甚至可以自定义迭代器的子类以支持新的遍历。
 * 增加新的聚合类和迭代器类都很方便，无须修改原有代码。
 * 封装性良好，为遍历不同的聚合结构提供一个统一的接口。
 *
 * 其主要缺点是：增加了类的个数，这在一定程度上增加了系统的复杂性。
 *
 * 迭代器模式是通过将聚合对象的遍历行为分离出来，抽象成迭代器类来实现的，其目的是在不暴露聚合对象的内部结构的情况下，
 * 让外部代码透明地访问聚合的内部数据。
 * 1. 模式的结构
 * 迭代器模式主要包含以下角色。
 * 抽象聚合（Aggregate）角色：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
 * 具体聚合（ConcreteAggregate）角色：实现抽象聚合类，返回一个具体迭代器的实例。
 * 抽象迭代器（Iterator）角色：定义访问和遍历聚合元素的接口，通常包含 hasNext()、first()、next() 等方法。
 * 具体迭代器（Concretelterator）角色：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。
 */
public class IteratorTest {

   public static final String TAG = IteratorTest.class.toString();

   public void tryTest(){
      Aggregate concreteAggregate = new ConcreteAggregate();
      concreteAggregate.add("zs");
      concreteAggregate.add("ls");
      concreteAggregate.add("wz");
      Iterator iterator = concreteAggregate.getIterator();
      while (iterator.hasNest()){
         Log.d(TAG, "tryTest: the next is "+iterator.next());
      }
      Object first = iterator.first();
      Log.d(TAG, "tryTest: first is "+first);
   }

   public interface Aggregate{
      void add(Object s);
      void remove(Object s);
      Iterator getIterator();
   }

   public static class ConcreteAggregate implements Aggregate{
      private List<Object> list;

      public ConcreteAggregate() {
         list = new ArrayList<>();
      }

      @Override
      public void add(Object s) {
         list.add(s);
      }

      @Override
      public void remove(Object s) {
         list.remove(s);
      }

      @Override
      public Iterator getIterator() {
         return new Concretelterator(list);
      }
   }

   public interface Iterator{
      Object first();
      Object next();
      boolean hasNest();
   }

   public static class Concretelterator implements Iterator{
      private List<Object> list;
      private int index = -1;

      public Concretelterator(List<Object> list) {
         this.list = list;
      }

      @Override
      public Object first() {
         index = 0;
         return list.get(index);
      }

      @Override
      public Object next() {
         if(hasNest()){
            return list.get(++index);
         }
         return null;
      }

      @Override
      public boolean hasNest() {
         if(index<list.size()-1){
            return true;
         }
         return false;
      }
   }
}
