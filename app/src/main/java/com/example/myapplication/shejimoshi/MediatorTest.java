package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 中介者模式
 * 中介者（Mediator）模式的定义：定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，
 * 且可以独立地改变它们之间的交互。中介者模式又叫调停模式，它是迪米特法则的典型应用。
 *
 * 中介者模式是一种对象行为型模式，其主要优点如下。
 * 类之间各司其职，符合迪米特法则。
 * 降低了对象之间的耦合性，使得对象易于独立地被复用。
 * 将对象间的一对多关联转变为一对一的关联，提高系统的灵活性，使得系统易于维护和扩展。
 *
 * 其主要缺点是：中介者模式将原本多个对象直接的相互依赖变成了中介者和多个同事类的依赖关系。当同事类越多时，
 * 中介者就会越臃肿，变得复杂且难以维护。
 *
 * 1. 模式的结构
 * 中介者模式包含以下主要角色。
 * 抽象中介者（Mediator）角色：它是中介者的接口，提供了同事对象注册与转发同事对象信息的抽象方法。
 * 具体中介者（Concrete Mediator）角色：实现中介者接口，定义一个 List 来管理同事对象，协调各个同事角色之间的交互关系，因此它依赖于同事角色。
 * 抽象同事类（Colleague）角色：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法，实现所有相互影响的同事类的公共功能。
 * 具体同事类（Concrete Colleague）角色：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。
 */
public class MediatorTest {

   public static final String TAG = MediatorTest.class.toString();

   public void tryTest(){
      Mediator mediator = new ConcreteMediator();
      Colleague concreteColleagueOne = new ConcreteColleagueOne();
      Colleague concreteColleagueTwo = new ConcreteColleagueTwo();
      mediator.regist(concreteColleagueOne);
      mediator.regist(concreteColleagueTwo);
      concreteColleagueOne.send();
      concreteColleagueTwo.send();
   }

   public void tryCusTest(){
      Medium medium = new EstateMedium();
      Customer seller = new Seller("zhangsan");
      Customer buyer = new Buyer("lisi");
      medium.register(seller);
      medium.register(buyer);
      buyer.send("i want a house");
      seller.send("i have a house you wanted");
   }

   public void tryInstancetest(){
      SimpleMediator simpleMediator = SimpleMediator.getInstace();
      SimpleColleague simpleColleagueOne = new SimpleColleagueOne();
      SimpleColleague simpleColleagueTwo = new SimpleColleagueTwo();
      simpleMediator.register(simpleColleagueOne);
      simpleMediator.register(simpleColleagueTwo);
      simpleColleagueOne.send();
      simpleColleagueTwo.send();
   }

   public static abstract class Mediator{
      abstract void regist(Colleague colleague);//注册对象
      abstract void relay(Colleague colleague);//转发对象
   }

   public static class ConcreteMediator extends Mediator{

      private List<Colleague> list;

      public ConcreteMediator(){
         list = new ArrayList<>();
      }

      @Override
      void regist(Colleague colleague) {
         if(!list.contains(colleague)){
            list.add(colleague);
            colleague.setMediator(this);
         }
      }

      @Override
      void relay(Colleague colleague) {
         for (Colleague c : list){
            if(!c.equals(colleague)){
               c.receive();
            }
         }
      }
   }

   public static abstract class Colleague{
      private Mediator mediator;

      public void setMediator(Mediator m){
         this.mediator = m;
      }

      public Mediator getMediator(){
         return mediator;
      }

      abstract void receive();
      abstract void send();
   }

   public static class ConcreteColleagueOne extends Colleague{

      @Override
      void receive() {
         Log.d(TAG, "receive: this is ConcreteColleagueOne s receive");
      }

      @Override
      void send() {
         Log.d(TAG, "send: this is ConcreteColleagueOne s send");
         getMediator().relay(this);
      }
   }

   public static class ConcreteColleagueTwo extends Colleague{

      @Override
      void receive() {
         Log.d(TAG, "receive: this is ConcreteColleagueTwo s receive");
      }

      @Override
      void send() {
         Log.d(TAG, "receive: this is ConcreteColleagueTwo s send");
         getMediator().relay(this);
      }
   }

   /**
    * 【例1】用中介者模式编写一个“韶关房地产交流平台”程序。
    *
    * 说明：韶关房地产交流平台是“房地产中介公司”提供给“卖方客户”与“买方客户”进行信息交流的平台，比较适合用中介者模式来实现。
    *
    * 首先，定义一个中介公司（Medium）接口，它是抽象中介者，它包含了客户注册方法 register(Customer member)
    * 和信息转发方法 relay(String from,String ad)；再定义一个韶关房地产中介（EstateMedium）公司，它是具体中介者类，
    * 它包含了保存客户信息的 List 对象，并实现了中介公司中的抽象方法。
    *
    * 然后，定义一个客户（Customer）类，它是抽象同事类，其中包含了中介者的对象，和发送信息的 send(String ad)
    * 方法与接收信息的 receive(String from，String ad) 方法的接口
    *
    * 最后，定义卖方（Seller）类和买方（Buyer）类，它们是具体同事类，是客户（Customer）类的子类，
    * 它们实现了父类中的抽象方法，通过中介者类进行信息交流
    */

   public interface Medium{
      void register(Customer c);
      void relay(String form,String ad);
   }

   public static class EstateMedium implements Medium{

      private List<Customer> list;

      public EstateMedium() {
         list = new ArrayList<>();
      }

      @Override
      public void register(Customer c) {
         list.add(c);
         c.setMedium(this);
      }

      @Override
      public void relay(String form, String ad) {
         for (Customer c : list){
            if(!c.getName().equals(form)){
               c.send(ad);
            }
         }
      }
   }

   public static abstract class Customer{
      private Medium medium;
      private String name;

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public Medium getMedium() {
         return medium;
      }

      public void setMedium(Medium medium) {
         this.medium = medium;
      }

      abstract void receive(String from,String ad);
      abstract void send(String ad);
   }

   public static class Seller extends Customer{
      public Seller(String name){
         setName(name);
      }
      @Override
      void receive(String from, String ad) {
         Log.d(TAG, "receive: this is Seller get the "+ad+" form "+from);
      }

      @Override
      void send(String ad) {
         getMedium().relay(getName(),ad);
      }
   }

   public static class Buyer extends Customer{

      public Buyer(String name) {
         setName(name);
      }

      @Override
      void receive(String from, String ad) {
         Log.d(TAG, "receive: this is Buyer get the ad :"+ad+" form "+from);
      }

      @Override
      void send(String ad) {
         getMedium().relay(getName(),ad);
      }
   }

   /**
    * 在实际开发中，通常采用以下两种方法来简化中介者模式，使开发变得更简单。
    * 不定义中介者接口，把具体中介者对象实现成为单例。
    * 同事对象不持有中介者，而是在需要的时候直接获取中介者对象并调用。
    */

   public static class SimpleMediator{

      private static volatile SimpleMediator simpleMediator;

      private List<SimpleColleague> list;

      public static synchronized SimpleMediator getInstace(){
         if(simpleMediator==null){
            simpleMediator = new SimpleMediator();
         }
         return simpleMediator;
      }

      public void register(SimpleColleague colleague){
         if(list==null){
            list = new ArrayList<>();
         }
         list.add(colleague);
      }

      public void relay(SimpleColleague colleague){
         for (SimpleColleague simpleColleague : list){
            if(!simpleColleague.equals(colleague)){
               simpleColleague.receive();
            }
         }
      }
   }

   public interface SimpleColleague{
      void receive();
      void send();
   }

   public static class SimpleColleagueOne implements SimpleColleague{

      @Override
      public void receive() {
         Log.d(TAG, "receive: this is SimpleColleagueOne get this receive ");
      }

      @Override
      public void send() {
         SimpleMediator simpleMediator = SimpleMediator.getInstace();
         simpleMediator.relay(this);
      }
   }

   public static class SimpleColleagueTwo implements SimpleColleague{

      @Override
      public void receive() {
         Log.d(TAG, "receive: this is SimpleColleagueTwo get the receive");
      }

      @Override
      public void send() {
         SimpleMediator instace = SimpleMediator.getInstace();
         instace.relay(this);
      }
   }

}
