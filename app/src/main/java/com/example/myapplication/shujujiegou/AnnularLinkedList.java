package com.example.myapplication.shujujiegou;

import android.util.Log;

/**
 * 单向环形链表
 * 可解决约瑟夫问题：约瑟夫问题是个有名的问题：N个人围成一圈，从第一个开始报数，第M个将被杀掉，最后剩下一个，其余人都将被杀掉。
 * 例如N=6，M=5，被杀掉的顺序是：5，4，6，2，3。
 */
public class AnnularLinkedList<E> {

    private AnnularLinkedNode temp;//指针变量
    private AnnularLinkedNode first;//出走变量
    private AnnularLinkedNode head;//头节点
    public static final String TAG = AnnularLinkedList.class.toString();

    public AnnularLinkedList(){
    }

    //添加
    public void add(E e){
        AnnularLinkedNode node = new AnnularLinkedNode(e);
        if(head==null){
            head = node;
            node.next = head;
            return;
        }
        temp = head;
        while (temp.next!=head){
            temp = temp.next;
        }
        temp.next = node;
        node.next = head;
    }

    public void showList(){
        if(head==null){
            Log.d(TAG, "showList: "+"list is null");
            return;
        }
        temp = head;
        while (temp.next!=head){
            Log.d(TAG, "showList: "+temp.toString());
            temp = temp.next;
        }
        Log.d(TAG, "showList: "+temp.toString());
    }

    public int getSize(){
        if(head==null){
            Log.d(TAG, "showList: "+"list is null");
            return 0;
        }
        int count = 0;
        temp = head;
        while (temp.next!=head){
            count++;
            temp = temp.next;
        }
        count++;
        return count;
    }

    //解决约瑟夫问题
    public void outOfList(int m){
        temp = head;
        while (temp.next!=head){
            temp = temp.next;
        }
        int count = m;
        first = temp.next;
        while (temp.next!=first.next){
            if((count-1)==0){
                //找到了，出链表
                Log.d(TAG, "outOfList: "+first.toString());
                first =  first.next;
                temp.next = first;
                count = m;
            }else {
                //没找到，报数，并后移
                count--;
                temp = temp.next;
                first = temp.next;
            }
        }
        Log.d(TAG, "outOfList: "+first.toString());
    }

    public class AnnularLinkedNode<E>{
        private E e;
        private AnnularLinkedNode next;

        public AnnularLinkedNode(E e) {
            this.e = e;
        }

        @Override
        public String toString() {
            return "AnnularLinkedNode{" +
                    "e=" + e +
                    '}';
        }
    }
}
