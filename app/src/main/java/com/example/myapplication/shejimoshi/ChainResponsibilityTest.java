package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 责任链模式
 * 1. 模式的结构
 * 职责链模式主要包含以下角色。
 * 抽象处理者（Handler）角色：定义一个处理请求的接口，包含抽象处理方法和一个后继连接。
 * 具体处理者（Concrete Handler）角色：实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者。
 * 客户类（Client）角色：创建处理链，并向链头的具体处理者对象提交请求，它不关心处理细节和请求的传递过程。
 *
 * 责任链模式的本质是解耦请求与处理，让请求在处理链中能进行传递与被处理；理解责任链模式应当理解其模式，而不是其具体实现。
 * 责任链模式的独到之处是将其节点处理者组合成了链式结构，并允许节点自身决定是否进行请求处理或转发，相当于让请求流动起来。
 */
public class ChainResponsibilityTest {

   public static final String TAG = ChainResponsibilityTest.class.toString();

   public void tryTest(){
      Leader classAdciser = new ClassAdviser();
      Leader departmentHead = new DepartmentHead();
      classAdciser.setLeader(departmentHead);
      Leader dean = new Dean();
      departmentHead.setLeader(dean);
      classAdciser.handleRequest(8);
   }

   /**
    * 分析：假如规定学生请假小于或等于 2 天，班主任可以批准；小于或等于 7 天，系主任可以批准；小于或等于 10 天，
    * 院长可以批准；其他情况不予批准；这个实例适合使用职责链模式实现。
    *
    * 首先，定义一个领导类（Leader），它是抽象处理者，包含了一个指向下一位领导的指针 next 和一个处理假条的抽象处理方法
    * handleRequest(int LeaveDays)；然后，定义班主任类（ClassAdviser）、系主任类（DepartmentHead）和院长类（Dean），
    * 它们是抽象处理者的子类，是具体处理者，必须根据自己的权力去实现父类的 handleRequest(int LeaveDays) 方法，
    * 如果无权处理就将假条交给下一位具体处理者，直到最后；客户类负责创建处理链，并将假条交给链头的具体处理者（班主任）
    */

   public static abstract class Leader{
      private Leader leader;

      public Leader getLeader() {
         return leader;
      }

      public void setLeader(Leader leader) {
         this.leader = leader;
      }

      abstract void handleRequest(int LeaveDays);
   }

   //班主任
   public static class ClassAdviser extends Leader{

      @Override
      void handleRequest(int LeaveDays) {
         if(LeaveDays<=2){
            Log.d(TAG, "handleRequest: this is ClassAdviser check");
         }else {
            if(getLeader()!=null){
               getLeader().handleRequest(LeaveDays);
            }else {
               Log.d(TAG, "handleRequest: no one can check this days");
            }
         }
      }
   }

   //系主任
   public static class DepartmentHead extends Leader{

      @Override
      void handleRequest(int LeaveDays) {
         if(LeaveDays<=7){
            Log.d(TAG, "handleRequest: this is DepartmentHead check");
         }else {
            if(getLeader()!=null){
               getLeader().handleRequest(LeaveDays);
            }else {
               Log.d(TAG, "handleRequest: no on can check this day");
            }
         }
      }
   }

   //院长类
   public static class Dean extends Leader{

      @Override
      void handleRequest(int LeaveDays) {
         if(LeaveDays<=10){
            Log.d(TAG, "handleRequest: this is Dean check");
         }else {
            if(getLeader()!=null){
               getLeader().handleRequest(LeaveDays);
            }else {
               Log.d(TAG, "handleRequest: no one can check this day");
            }
         }
      }
   }
}
