package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 观察者模式
 * 实现观察者模式时要注意具体目标对象和具体观察者对象之间不能直接调用，否则将使两者之间紧密耦合起来，这违反了面向对象的设计原则。
 * 1. 模式的结构
 * 观察者模式的主要角色如下。
 * 抽象主题（Subject）角色：也叫抽象目标类，它提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的抽象方法。
 * 具体主题（Concrete Subject）角色：也叫具体目标类，它实现抽象目标中的通知方法，当具体主题的内部状态发生改变时，通知所有注册过的观察者对象。
 * 抽象观察者（Observer）角色：它是一个抽象类或接口，它包含了一个更新自己的抽象方法，当接到具体主题的更改通知时被调用。
 * 具体观察者（Concrete Observer）角色：实现抽象观察者中定义的抽象方法，以便在得到目标的更改通知时更新自身的状态。
 */
public class ObserverTest {

   public static final String TAG = Observer.class.toString();

   public void tryTest(){
      Subject subject = new ConcreteSubject();
      Observer observer = new ConcreteObserverOne();
      Observer observer1 = new ConcreteObserverTwo();
      subject.setObserver(observer);
      subject.setObserver(observer1);
      subject.notifyA();
   }

   public void tryCompanyTest(){
      Rate rate = new RMBrate(-9);
      Company importCompany = new ImportCompany();
      rate.setCompany(importCompany);
      Company exportCompany = new ExportCompany();
      rate.setCompany(exportCompany);
      rate.change();
      rate.setMoney(10);
      rate.change();
   }

   public void tryRingTest(){
      BellEventSource source = new BellEventSource();
      BellEventListener teachEventListener = new TeachEventListener();
      BellEventListener stuEventListener = new StuEventListener();
      source.setListener(teachEventListener);
      source.setListener(stuEventListener);
      source.ring(true);
   }

   public void tryJavaTest(){
      OilFutures oilFutures = new OilFutures();
      Bull bull = new Bull();
      oilFutures.addObserver(bull);
      Bear bear = new Bear();
      oilFutures.addObserver(bear);
      oilFutures.setPrice(10);
   }

   public static abstract class Subject{
      private List<Observer> list;
      public Subject(){
         list = new ArrayList<>();
      }

      public List<Observer> getList() {
         return list;
      }

      public void setList(List<Observer> list) {
         this.list = list;
      }

      public void setObserver(Observer observer){
         list.add(observer);
      }

      public void removeObserver(Observer observer){
         list.remove(observer);
      }

      abstract void notifyA();
   }

   public static class ConcreteSubject extends Subject{

      @Override
      void notifyA() {
         Log.d(TAG, "notifyA: now change and do resonpse");
         for(Observer observer : getList()){
            observer.resonpse();
         }
      }
   }

   public interface Observer{
      void resonpse();
   }

   public static class ConcreteObserverOne implements Observer{

      @Override
      public void resonpse() {
         Log.d(TAG, "resonpse: now is ConcreteObserverOne do resonpse");
      }
   }

   public static class ConcreteObserverTwo implements Observer{

      @Override
      public void resonpse() {
         Log.d(TAG, "resonpse: now is ConcreteObserverTwo do Observer");
      }
   }

   /**
    * 例1 利用观察者模式设计一个程序，分析“人民币汇率”的升值或贬值对进口公司进口产品成本或出口公司的出口产品收入以及公司利润率的影响。
    *
    * 分析：当“人民币汇率”升值时，进口公司的进口产品成本降低且利润率提升，出口公司的出口产品收入降低且利润率降低；
    * 当“人民币汇率”贬值时，进口公司的进口产品成本提升且利润率降低，出口公司的出口产品收入提升且利润率提升。
    *
    * 这里的汇率（Rate）类是抽象目标类，它包含了保存观察者（Company）的 List 和增加/删除观察者的方法，
    * 以及有关汇率改变的抽象方法 change(int number)；而人民币汇率（RMBrate）类是具体目标，
    * 它实现了父类的 change(int number) 方法，即当人民币汇率发生改变时通过相关公司；公司（Company）类是抽象观察者，
    * 它定义了一个有关汇率反应的抽象方法 response(int number)；进口公司（ImportCompany）类和出口公司（ExportCompany）
    * 类是具体观察者类，它们实现了父类的 response(int number) 方法，即当它们接收到汇率发生改变的通知时作为相应的反应。
    */

   public static abstract class Rate{
      private List<Company> list;
      private int money;

      public Rate(int money) {
         list = new ArrayList<>();
         this.money = money;
      }

      public int getMoney() {
         return money;
      }

      public void setMoney(int money) {
         this.money = money;
      }

      public List<Company> getList() {
         return list;
      }

      public void setList(List<Company> list) {
         this.list = list;
      }

      public void setCompany(Company company){
         list.add(company);
      }

      public void removeCompany(Company company){
         list.remove(company);
      }

      abstract void change();
   }

   public static class RMBrate extends Rate{

      public RMBrate(int money) {
         super(money);
      }

      @Override
      void change() {
         for (Company company : getList()){
            company.response(getMoney());
         }
      }
   }

   public interface Company{
      void response(int money);
   }

   public static class ImportCompany implements Company{

      @Override
      public void response(int money) {
         if(money>0){
            Log.d(TAG, "response: ImportCompany s rate is up becuse the rate is up "+money+"%");
         }else {
            Log.d(TAG, "response: ImportCompany s rate is down becuse the rate is down "+money+"%");
         }
      }
   }

   public static class ExportCompany implements Company{

      @Override
      public void response(int money) {
         if(money>0){
            Log.d(TAG, "response: ExportCompany s rate is down becuse the rate is up "+money+"%");
         }else {
            Log.d(TAG, "response: ExportCompany s rate is up becuse the rate is down "+money+"%");
         }
      }
   }


   /**
    * 【例2】利用观察者模式设计一个学校铃声的事件处理程序。
    *
    * 分析：在本实例中，学校的“铃”是事件源和目标，“老师”和“学生”是事件监听器和具体观察者，“铃声”是事件类。
    * 学生和老师来到学校的教学区，都会注意学校的铃，这叫事件绑定；当上课时间或下课时间到，会触发铃发声，
    * 这时会生成“铃声”事件；学生和老师听到铃声会开始上课或下课，这叫事件处理。这个实例非常适合用观察者模式实现
    *
    * 现在用“观察者模式”来实现该事件处理模型。
    *
    * 首先，定义一个铃声事件（RingEvent）类，它记录了铃声的类型（上课铃声/下课铃声）。
    *
    * 再定义一个学校的铃（BellEventSource）类，它是事件源，是观察者目标类，该类里面包含了监听器容器 listener，
    * 可以绑定监听者（学生或老师），并且有产生铃声事件和通知所有监听者的方法。
    *
    * 然后，定义铃声事件监听者（BellEventListener）类，它是抽象观察者，它包含了铃声事件处理方法 heardBell(RingEvent e)。
    *
    * 最后，定义老师类（TeachEventListener）和学生类（StuEventListener），它们是事件监听器，是具体观察者，听到铃声会去上课或下课。
    */

   public static class RingEvent{
      private boolean ring;

      public boolean isRing() {
         return ring;
      }

      public void setRing(boolean ring) {
         this.ring = ring;
      }
   }

   public static class BellEventSource{
      private List<BellEventListener> list;

      public BellEventSource() {
         list = new ArrayList<>();
      }

      public List<BellEventListener> getList() {
         return list;
      }

      public void setList(List<BellEventListener> list) {
         this.list = list;
      }

      public void setListener(BellEventListener listener){
         list.add(listener);
      }

      public void removeListener(BellEventListener listener){
         list.remove(listener);
      }

      public void ring(boolean ring){
         Log.d(TAG, "ring: this is "+(ring?"up":"down"));
         RingEvent event = new RingEvent();
         event.setRing(ring);
         doListener(event);
      }

      private void doListener(RingEvent ringEvent){
         for (BellEventListener listener : list){
            listener.handleRing(ringEvent);
         }
      }
   }

   public interface BellEventListener{
      void handleRing(RingEvent ringEvent);
   }

   public static class TeachEventListener implements BellEventListener{

      @Override
      public void handleRing(RingEvent ringEvent) {
         if(ringEvent.isRing()){
            Log.d(TAG, "handleRing: teacher:classes we get class");
         }else {
            Log.d(TAG, "handleRing: teacher:classes we out of class");
         }
      }
   }

   public static class StuEventListener implements BellEventListener{

      @Override
      public void handleRing(RingEvent ringEvent) {
         if(ringEvent.isRing()){
            Log.d(TAG, "handleRing: student is up class");
         }else {
            Log.d(TAG, "handleRing: student is out of class");
         }
      }
   }

   /**
    * 在 Java 中，通过 java.util.Observable 类和 java.util.Observer 接口定义了观察者模式，只要实现它们的子类就可以编写观察者模式实例。
    * 1. Observable类
    * Observable 类是抽象目标类，它有一个 Vector 向量，用于保存所有要通知的观察者对象，下面来介绍它最重要的 3 个方法。
    * void addObserver(Observer o) 方法：用于将新的观察者对象添加到向量中。
    * void notifyObservers(Object arg) 方法：调用向量中的所有观察者对象的 update() 方法，通知它们数据发生改变。
    * 通常越晚加入向量的观察者越先得到通知。
    * void setChange() 方法：用来设置一个 boolean 类型的内部标志位，注明目标对象发生了变化。当它为真时，notifyObservers()
    * 才会通知观察者。
    * 2. Observer 接口
    * Observer 接口是抽象观察者，它监视目标对象的变化，当目标对象发生变化时，观察者得到通知，并调用
    * void update(Observable o,Object arg) 方法，进行相应的工作。
    */

   /**
    * 【例3】利用 Observable 类和 Observer 接口实现原油期货的观察者模式实例。
    *
    * 分析：当原油价格上涨时，空方伤心，多方局兴；当油价下跌时，空方局兴，多方伤心。本实例中的抽象目标（Observable）
    * 类在 Java 中已经定义，可以直接定义其子类，即原油期货（OilFutures）类，它是具体目标类，该类中定义一个
    * SetPriCe(float price) 方法，当原油数据发生变化时调用其父类的 notifyObservers(Object arg)
    * 方法来通知所有观察者；另外，本实例中的抽象观察者接口（Observer）在 Java 中已经定义，只要定义其子类，
    * 即具体观察者类（包括多方类 Bull 和空方类 Bear），并实现 update(Observable o,Object arg) 方法即可
    */

   public static class OilFutures extends Observable{
      private int price;

      public int getPrice() {
         return price;
      }

      public void setPrice(int price) {
         Log.d(TAG, "setPrice: Oil price change "+price);
         super.setChanged();//设置内部标志位，注明数据发生变化
         super.notifyObservers(price); //通知观察者价格改变了
         this.price = price;
      }
   }

   public static class Bull implements java.util.Observer {

      @Override
      public void update(Observable o, Object arg) {
         int price = (int) arg;
         if(price>0){
            Log.d(TAG, "update: the price is up Bull happy");
         }else {
            Log.d(TAG, "update: the price is down Bull unhappy");
         }
      }
   }

   public static class Bear implements java.util.Observer{

      @Override
      public void update(Observable o, Object arg) {
         int price = (int) arg;
         if(price>0){
            Log.d(TAG, "update: the price is up Bear unhappy");
         }else {
            Log.d(TAG, "update: the price is down Bear happy");
         }
      }
   }
}
