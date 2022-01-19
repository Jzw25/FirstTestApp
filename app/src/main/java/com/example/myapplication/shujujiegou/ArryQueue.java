package com.example.myapplication.shujujiegou;

import android.util.Log;

/**
 * 队列：由数组实现,简单实现int类型队列
 */
public class ArryQueue {
    private final static String TAG = ArryQueue.class.toString();
    private int[] queue;//队列数组
    private int front;//队头节点
    private int renr;//队尾节点
    private int maxSize;//队列长度

    public ArryQueue(){
        new ArryQueue(10);
    }

    public ArryQueue(int maxSize){
        this.maxSize = maxSize;
        front = -1;
        renr = -1;
        queue = new int[maxSize];
    }

    //队列是否满
    public boolean isFull(){
        return renr==maxSize-1;
    }

    //队列是否为空
    public boolean isEmpty(){
        return renr==front;
    }

    //入队
    public boolean setQueue(int n){
        if(isFull()){
            Log.d(TAG,"ArryQueue已满");
            return false;
        }
        renr++;
        queue[renr]= n;
        return true;
    }

    //出队
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("ArryQueue为空");
        }
        front++;
        return queue[front];
    }

    //展示队列
    public void showQueue(){
        if(isEmpty()){
            throw new RuntimeException("ArryQueue为空");
        }
        for (int i : queue){
            Log.d(TAG, "showQueue: "+i);
        }
    }

    //展示头
    public int showHead(){
        if(isEmpty()){
            throw new RuntimeException("ArryQueue为空");
        }
        return queue[front+1];
    }
}
