package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式
 * 1. 模式的结构
 * 命令模式包含以下主要角色。
 * 抽象命令类（Command）角色：声明执行命令的接口，拥有执行命令的抽象方法 execute()。
 * 具体命令类（Concrete Command）角色：是抽象命令类的具体实现类，它拥有接收者对象，并通过调用接收者的功能来完成命令要执行的操作。
 * 实现者/接收者（Receiver）角色：执行命令功能的相关操作，是具体命令对象业务的真正实现者。
 * 调用者/请求者（Invoker）角色：是请求的发送者，它通常拥有很多的命令对象，并通过访问命令对象来执行相关请求，它不直接访问接收者。
 */
public class CommandTest {

   public static final String TAG = CommandTest.class.toString();

   public void tryTest(){
      Command command = new ConcreteCommand();
      Invoker invoker = new Invoker(command);
      invoker.call();
   }

   private void tryTestMore(){
      InvokerMore invokerMore = new InvokerMore();
      Command command = new ConcreteCommand();
      invokerMore.setCommand(command);
      Command command1 = new ConcreteCommandSecond();
      invokerMore.setCommand(command1);
      invokerMore.excute();
   }

   interface Command{
      void execute();
   }

   public static class ConcreteCommand implements Command{
      private CommandReceiver receiver;

      public ConcreteCommand(){
         receiver = new CommandReceiver();
      }

      @Override
      public void execute() {
         receiver.execute();
      }
   }

   public static class CommandReceiver{
      public void execute(){
         Log.d(TAG, "execute: 具体调用者CommandReceiver的execute");
      }

      private void executSecond(){
         Log.d(TAG, "executSecond: 具体调用者CommandReceiver的executSecond");
      }
   }

   public static class Invoker{
      private Command command;

      public Invoker(Command command) {
         this.command = command;
      }

      public void call(){
         command.execute();
      }

   }

   public static class ConcreteCommandSecond implements Command{
      private CommandReceiver commandReceiver;

      public ConcreteCommandSecond() {
         commandReceiver = new CommandReceiver();
      }

      @Override
      public void execute() {
         commandReceiver.executSecond();
      }
   }

   /**
    * 在软件开发中，有时将命令模式与前面学的组合模式联合使用，这就构成了宏命令模式，也叫组合命令模式。
    * 宏命令包含了一组命令，它充当了具体命令与调用者的双重角色，执行它时将递归调用它所包含的所有命令
    */
   public static class InvokerMore{
      private List<Command> list;

      public InvokerMore() {
         list = new ArrayList<>();
      }

      public void setCommand(Command command){
         list.add(command);
      }

      public Command getCommand(int i){
         return list.get(i);
      }

      public void removeCommand(Command command,int i){
         int count = -1;
         if(i>0){
            count = i;
         }
         if(command==null&&count>0){
            list.remove(i);
         }else if(command!=null&&count<0){
            list.remove(command);
         }else if (command==null&&count<0){
            return;
         }else {
            list.remove(command);
         }
      }

      private void excute(){
         for (Command command : list){
            command.execute();
         }
      }

   }

}
