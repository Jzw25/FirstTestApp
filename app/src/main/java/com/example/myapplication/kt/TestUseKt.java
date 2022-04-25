package com.example.myapplication.kt;

import java.util.List;

public class TestUseKt {
   private void tryTest(){
      MyTestKt myTestKt = new MyTestKt();
      myTestKt.testFilter();

      //调用jvnname修饰过的类
      zhuan.show();
      
      //调用数组
      List<String> list = zhuan.getList();
      for (String s : list) {
         
      }

      //调用加了jvmfiled注解的变量
      List<String> jvmfileList = zhuan.jvmfileList;
      for (String s : jvmfileList) {
         
      }

      //调用有默认值的kt函数
      zhuan.toast("zs");

//      //如果不加jvmfiled要这样调用
//      TestJvmStatic.Companion.getNamm();
//      //如果不加jvmstatic要这样调用
//      TestJvmStatic.Companion.show("yt");

      //调用加了jvmstatic的
      TestJvmStatic.show("yt");
      String namm = TestJvmStatic.namm;
   }

}
