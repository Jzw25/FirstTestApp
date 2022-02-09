package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 模板方法模式
 * 1. 模式的结构
 * 模板方法模式包含以下主要角色。
 * 1）抽象类/抽象模板（Abstract Class）
 * 抽象模板类，负责给出一个算法的轮廓和骨架。它由一个模板方法和若干个基本方法构成。这些方法的定义如下。
 *
 * ① 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。
 *
 * ② 基本方法：是整个算法中的一个步骤，包含以下几种类型。
 * 抽象方法：在抽象类中声明，由具体子类实现。
 * 具体方法：在抽象类中已经实现，在具体子类中可以继承或重写它。
 * 钩子方法：在抽象类中已经实现，包括用于判断的逻辑方法和需要子类重写的空方法两种。
 */
public class TemplateMethodTest {

   public static final String TAG = TemplateMethodTest.class.toString();

   public void tryTest(){
      TemplateMethod templateMethod = new TemplateMethodAbstraction();
      templateMethod.method();
   }

   public void tryHookTest(){
      HookTemplateMethod templateMethod = new HookTemplateMethodAbs();
      templateMethod.method();
   }

   public static abstract class TemplateMethod{

      public void method(){
         some();
         function1();
         function2();
      }

      public void some(){
         Log.d(TAG, "some: this is all should do");
      }

      abstract void function1();

      abstract void function2();
   }

   public static class TemplateMethodAbstraction extends TemplateMethod{

      @Override
      void function1() {
         Log.d(TAG, "function1: this is TemplateMethodAbstraction do function1");
      }

      @Override
      void function2() {
         Log.d(TAG, "function2: this is TemplateMethodAbstraction do function2");
      }
   }

   /**
    * 在模板方法模式中，基本方法包含：抽象方法、具体方法和钩子方法，正确使用“钩子方法”可以使得子类控制父类的行为。
    * 如下面例子中，可以通过在具体子类中重写钩子方法 HookMethod1() 和 HookMethod2() 来改变抽象父类中的运行结果
    */

   //带钩子方法的抽象类
   public static abstract class HookTemplateMethod{
      public void method(){
         method1();
         absMethod();
         if(hookMethod()){
            hookMethod2();
         }
         absMethod2();
      }

      public void method1(){
         Log.d(TAG, "method1: this is HookTemplateMethod s method1");
      }

      abstract void absMethod();

      abstract void absMethod2();

      public boolean hookMethod(){
         return true;
      }

      public void hookMethod2(){
         Log.d(TAG, "hookMethod2: this is HookTemplateMethod s hookMethod2");
      }
   }

   public static class HookTemplateMethodAbs extends HookTemplateMethod{

      @Override
      void absMethod() {
         Log.d(TAG, "absMethod: this is HookTemplateMethodAbs absMethod");
      }

      @Override
      void absMethod2() {
         Log.d(TAG, "absMethod2: this is HookTemplateMethodAbs absMethod2");
      }

      @Override
      public void hookMethod2() {
         Log.d(TAG, "hookMethod2: this is HookTemplateMethodAbs hookMethod2");
      }

      @Override
      public boolean hookMethod() {
         return false;
      }
   }
}
