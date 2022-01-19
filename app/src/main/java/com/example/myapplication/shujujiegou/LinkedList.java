package com.example.myapplication.shujujiegou;

import android.util.Log;

import java.util.TooManyListenersException;

/**
 * 单链表
 */
public class LinkedList<T> {

    private LinkedBean head;//头部
    private LinkedBean temp;//当前节点
    private int size = 0;//链表长度
    public static final String TAG = LinkedList.class.toString();

    public LinkedList(){
        head = new LinkedBean(null);
    }

    //添加数据
    public void add(T t){
        LinkedBean linkedBean = new LinkedBean(t);
        temp = head;
        while (temp.next!=null){
            temp = temp.next;
        }
        temp.next = linkedBean;
        size++;
    }

    //循环打印数据
    public void showList(){
        temp = head;
        while (temp.next!=null){
            Log.d(TAG, "showList: "+temp.next.toString());
            temp = temp.next;
        }
    }

    //返回链表长度
    public int getSize(){
        int count = 0;
        temp = head;
        while (temp.next!=null){
            count++;
            temp = temp.next;
        }
        return count;
    }

    public LinkedBean getBean(int postion){
        int count = 0;
        temp = head;
        while (temp.next!=null){
            if(postion==count){
                break;
            }else {
                count++;
                temp = temp.next;
            }
        }
        if(postion>count){
            throw new IndexOutOfBoundsException("postion>ListSize,越界了");
        }
        return temp.next;
    }

    /**
     *  反转链表，头插法
     *  定义一个链表，头部指向缘链表得下一个，每次从头插入
     */
    public void reversalList(){
        if(head.next==null||head.next.next==null){
            return;
        }
        LinkedBean bean = new LinkedBean(null);
        temp = head.next;
        LinkedBean next = null;
        while (temp!=null){
                next = temp.next;
                temp.next = bean.next;
                bean.next = temp;
                temp = next;
        }
        head.next = bean.next;
    }

    //用栈反向输出
    public void useTask(){
        TestTask testTask = new TestTask(getSize());
        temp = head;
        while (temp.next!=null){
            testTask.add((int)temp.next.t);
            temp = temp.next;
        }
        testTask.showAll();
    }

    //内部维护一个类，转换加入得数据
    public static class LinkedBean<T>{
        private T t;
        private LinkedBean next;

        public LinkedBean(T t) {
            this.t = t;
        }

        @Override
        public String toString() {
            return "LinkedBean{" +
                    "t=" + t +
                    '}';
        }
    }
}
