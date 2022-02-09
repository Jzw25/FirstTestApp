package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 组合模式包含以下主要角色。
 * 抽象构件（Component）角色：它的主要作用是为树叶构件和树枝构件声明公共接口，并实现它们的默认行为。在透明式的组合模式中抽象构件还声明访问和管理子类的接口；在安全式的组合模式中不声明访问和管理子类的接口，管理工作由树枝构件完成。（总的抽象类或接口，定义一些通用的方法，比如新增、删除）
 * 树叶构件（Leaf）角色：是组合中的叶节点对象，它没有子节点，用于继承或实现抽象构件。
 * 树枝构件（Composite）角色 / 中间构件：是组合中的分支节点对象，它有子节点，用于继承和实现抽象构件。它的主要作用是存储和管理子部件，通常包含 Add()、Remove()、GetChild() 等方法。
 *
 * 组合模式分为透明式的组合模式和安全式的组合模式。
 * (1) 透明方式
 * 在该方式中，由于抽象构件声明了所有子类中的全部方法，所以客户端无须区别树叶对象和树枝对象，对客户端来说是透明的。但其缺点是：树叶构件本来没有 Add()、Remove() 及 GetChild() 方法，却要实现它们（空实现或抛异常），这样会带来一些安全性问题。
 * (2) 安全方式
 * 在该方式中，将管理子构件的方法移到树枝构件中，抽象构件和树叶构件没有对子对象的管理方法，这样就避免了上一种方式的安全性问题，但由于叶子和分支有不同的接口，客户端在调用时要知道树叶对象和树枝对象的存在，所以失去了透明性。
 */
public class CompositePatternTest {
   /**
    * 案例编写
    * 假如李先生到韶关“天街e角”生活用品店购物，用 1 个红色小袋子装了 2 包婺源特产（单价 7.9 元）、1 张婺源地图（单价 9.9 元）；
    * 用 1 个白色小袋子装了 2 包韶关香藉（单价 68 元）和 3 包韶关红茶（单价 180 元）；用 1 个中袋子装了前面的红色小袋子和
    * 1 个景德镇瓷器（单价 380 元）；用 1 个大袋子装了前面的中袋子、白色小袋子和 1 双李宁牌运动鞋（单价 198 元）。
    *
    * 最后“大袋子”中的内容有：{1 双李宁牌运动鞋（单价 198 元）、白色小袋子{2 包韶关香菇（单价 68 元）、3 包韶关红茶（单价 180 元）}
    * 、中袋子{1 个景德镇瓷器（单价 380 元）、红色小袋子{2 包婺源特产（单价 7.9 元）、1 张婺源地图（单价 9.9 元）}}}，
    * 现在要求编程显示李先生放在大袋子中的所有商品信息并计算要支付的总价。
    */

   public static final String TAG = CompositePatternTest.class.toString();

   public void tryTest(){
      Tree big = new Tree("big");
      Leaf leaf = new Leaf("李宁牌运动鞋",1,198);
      big.add(leaf);
      Tree whiteSmall = new Tree("whiteSmall");
      leaf = new Leaf("包韶关香菇",2,68);
      whiteSmall.add(leaf);
      leaf = new Leaf("包韶关红茶",3,180);
      whiteSmall.add(leaf);
      big.add(whiteSmall);
      Tree center = new Tree("center");
      leaf = new Leaf("景德镇瓷器",1,380);
      center.add(leaf);
      Tree redSmall = new Tree("redSmall");
      leaf = new Leaf("包婺源特产",2,8);
      redSmall.add(leaf);
      leaf = new Leaf("张婺源地图",1,10);
      redSmall.add(leaf);
      center.add(redSmall);
      big.add(center);

      big.show();
      int calcution = big.calcution();
      Log.d(TAG, "tryTest: last calcution is "+calcution);
   }

   /**
    * 抽象构建
    * 设定叶子功能，假定需要展示商品名称，单价。还有计算价格
    */
   public interface  Component{
      int calcution();
      void show();
   }

   /**
    * 叶子构建
    */
   public static class Leaf implements Component{
      private String name;//名称
      private int count;//数量
      private int price;//售价

      public Leaf(String name, int count, int price) {
         this.name = name;
         this.count = count;
         this.price = price;
      }

      @Override
      public int calcution() {
         return count*price;
      }

      @Override
      public void show() {
         Log.d(TAG, "show: Leaf :name is "+name+"price is "+price);
      }
   }

   /**
    * 树枝构建
    * 假设树枝为购物袋，分大中小，所有袋子可以装商品，大袋子可以装中袋子小袋子，中袋子可以装小袋子
    * 其实就是构建一棵树，大袋子为跟节点
    */

   public static class Tree implements Component{
      private String name;//袋子名称
      private List<Component> list;//袋子

      public Tree(String name) {
         this.name = name;
         list = new ArrayList<>();
      }

      //定义管理叶子节点方法，add，remove，get

      public void add(Component component){
         list.add(component);
      }

      public void remove(Component component){
         list.remove(component);
      }

      public Component getChild(int i){
         if(i<0||i>list.size()){
            throw new ArrayIndexOutOfBoundsException("postion is "+i+"but list size is "+list.size());
         }
         return list.get(i);
      }

      @Override
      public int calcution() {
         int count = 0;
         for (Component component : list){
            count+=component.calcution();
         }
         return count;
      }

      @Override
      public void show() {
         for (Component component : list){
            component.show();
         }
      }
   }
}
