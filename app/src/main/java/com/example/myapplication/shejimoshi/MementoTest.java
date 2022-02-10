package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 备忘录（Memento）模式
 * 定义：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢
 * 复到原先保存的状态。该模式又叫快照模式。
 *
 * 备忘录模式是一种对象行为型模式，其主要优点如下。
 * 提供了一种可以恢复状态的机制。当用户需要时能够比较方便地将数据恢复到某个历史的状态。
 * 实现了内部状态的封装。除了创建它的发起人之外，其他对象都不能够访问这些状态信息。
 * 简化了发起人类。发起人不需要管理和保存其内部状态的各个备份，所有状态信息都保存在备忘录中，并由管理者进行管理，
 * 这符合单一职责原则。
 *
 * 其主要缺点是：资源消耗大。如果要保存的内部状态信息过多或者特别频繁，将会占用比较大的内存资源。
 *
 * 模式的结构
 * 备忘录模式的主要角色如下。
 * 发起人（Originator）角色：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，
 * 它可以访问备忘录里的所有信息。
 * 备忘录（Memento）角色：负责存储发起人的内部状态，在需要的时候提供这些内部状态给发起人。
 * 管理者（Caretaker）角色：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容进行访问与修改。
 */
public class MementoTest {

    public static final String TAG = MementoTest.class.toString();

    public void tryTest(){
        Originator or = new Originator();
        Caretaker cr = new Caretaker();
        or.setState("S0");
        Log.d(TAG, "tryTest: "+"初始状态:" + or.getState());
        cr.setMemento(or.createMemento()); //保存状态
        or.setState("S1");
        Log.d(TAG, "tryTest: "+"新的状态:" + or.getState());
        or.restoreMemento(cr.getMemento()); //恢复状态
        Log.d(TAG, "tryTest: "+"恢复状态:" + or.getState());
    }
    //备忘录
    class Memento {
        private String state;
        public Memento(String state) {
            this.state = state;
        }
        public void setState(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }
    }
    //发起人
    class Originator {
        private String state;
        public void setState(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }
        public Memento createMemento() {
            return new Memento(state);
        }
        public void restoreMemento(Memento m) {
            this.setState(m.getState());
        }
    }
    //管理者
    class Caretaker {
        private Memento memento;
        public void setMemento(Memento m) {
            memento = m;
        }
        public Memento getMemento() {
            return memento;
        }
    }

    /**
     * 【例1】利用备忘录模式设计相亲游戏。
     *
     * 分析：假如有西施、王昭君、貂蝉、杨玉环四大美女同你相亲，你可以选择其中一位作为你的爱人；当然，如果你对前面
     * 的选择不满意，还可以重新选择，但希望你不要太花心；这个游戏提供后悔功能，用“备忘录模式”设计比较合适
     *
     * 首先，先设计一个美女（Girl）类，它是备忘录角色，提供了获取和存储美女信息的功能；然后，设计一个相亲者（You）类，
     * 它是发起人角色，它记录当前时刻的内部状态信息（临时妻子的姓名），并提供创建备忘录和恢复备忘录数据的功能；
     * 最后，定义一个美女栈（GirlStack）类，它是管理者角色，负责对备忘录进行管理，用于保存相亲者（You）前面选过
     * 的美女信息，不过最多只能保存 4 个，提供后悔功能。
     *
     */

    public void tryGrilTest(){
        Girl girl = new Girl("xs");
        You you = new You();
        you.setWifeName(girl.getName());
        you.createWife();
        GirlStack stack = new GirlStack(4);
        stack.push(girl);
        Girl girl1 = new Girl("dc");
        stack.push(girl1);
        you.restornWife(stack.pop());
    }

    public static class Girl{
        private String name;

        public Girl(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class You{
        private String wifeName;

        public String getWifeName() {
            return wifeName;
        }

        public void setWifeName(String wifeName) {
            this.wifeName = wifeName;
        }

        public Girl createWife(){
            return new Girl(wifeName);
        }

        public void restornWife(Girl girl){
            setWifeName(girl.getName());
        }
    }

    public static class GirlStack{
        private Girl[] girls;
        private int index;

        public GirlStack(int max) {
            girls = new Girl[max];
            index = -1;
        }

        public void push(Girl girl){
            index++;
            if(index>girls.length-1){
                Log.d(TAG, "push: so mush do not to push again!");
            }else {
                girls[index] = girl;
            }
        }

        public Girl pop(){
            return girls[index--];
        }
    }
}
