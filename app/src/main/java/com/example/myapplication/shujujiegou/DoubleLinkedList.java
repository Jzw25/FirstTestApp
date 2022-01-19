package com.example.myapplication.shujujiegou;

import android.util.Log;

/**
 * 双向链表
 */
public class DoubleLinkedList<E> {
    private DoubleNode head;//头节点
    private DoubleNode temp;//记录当前节点
    public static final String TAG = "DoubleLinkedList";

    public DoubleLinkedList() {
        head = new DoubleNode(null);
    }

    //添加
    public void add(E e){
        DoubleNode doubleNode = new DoubleNode(e);
        temp = head;
        while (temp.next!=null){
            temp = temp.next;
        }
        temp.next = doubleNode;
        doubleNode.pre = temp;
    }

    //获取链表长度
    public int getSize(){
        int count=0;
        temp = head;
        while (temp.next!=null){
            count++;
            temp = temp.next;
        }
        return count;
    }

    public void showList(){
        temp = head;
        while (temp.next!=null){
            Log.d(TAG, "showList: "+temp.next.toString());
            temp = temp.next;
        }
    }

    //插入
    public void set(){

    }

    //查找

    //修改

    //删除(下标)
    public void del(int postion){
        int count = 0;
        temp = head;
        while (temp.next!=null){
            if(postion==count){
                temp.next.pre = temp.pre;
                temp.pre.next = temp.next;
                break;
            }else {
                count++;
                temp = temp.next;
            }
        }
        if(postion>count){
            throw new IndexOutOfBoundsException("越界了");
        }
    }

    //删除(数据)
    public void del(E e){
        DoubleNode doubleNode = new DoubleNode(e);
        temp = head;
        while (temp.next!=null){
            if(temp.next.e == doubleNode.e){
                temp.next.pre = temp.pre;
                temp.pre.next = temp.next;
                break;
            }else {
                temp = temp.next;
            }
        }
    }

    //数据处理类
    public static class DoubleNode<E>{
        private E e;//当前数据
        private DoubleNode next;//后一个节点
        private DoubleNode pre;//前一个节点

        public DoubleNode(E e) {
            this.e = e;
        }
        @Override
        public String toString() {
            return "DoubleNode{" +
                    "e=" + e +
                    '}';
        }
    }
}
