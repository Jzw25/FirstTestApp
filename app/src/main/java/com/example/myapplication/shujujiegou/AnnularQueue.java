package com.example.myapplication.shujujiegou;

import android.util.Log;

/**
 * 环形队列：front队头出队，，指定从0开始
 * renr队尾入队，指定从0开始，数据指向renr+1，约定多出一个空间（防止满时与空时判断一致问题）
 */
public class AnnularQueue {
    private final static String TAG = ArryQueue.class.toString();
    private int[] queue;//队列数组
    private int front;//队头节点
    private int renr;//队尾节点
    private int maxSize;//队列长度

    public AnnularQueue(){
        new ArryQueue(10);
    }

    public AnnularQueue(int maxSize){
        this.maxSize = maxSize;
        front = 0;
        renr = 0;
        queue = new int[maxSize];
    }

    //队列是否满
    public boolean isFull(){
        return (renr+1)%maxSize==front;
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
        queue[renr]= n;
        renr = (renr+1)%maxSize;
        return true;
    }

    //出队
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("ArryQueue为空");
        }
        int vaule = queue[front];
        front = (front+1)%maxSize;
        return vaule;
    }

    //展示队列
    public void showQueue(){
        if(isEmpty()){
            throw new RuntimeException("ArryQueue为空");
        }
        for (int i = front ;i<front+countSize();i++){
            Log.d(TAG, "showQueue: "+queue[i%maxSize]);
        }
    }

    public int countSize() {
        return (renr+maxSize-front)%maxSize;
    }

    //展示头
    public int showHead(){
        if(isEmpty()){
            throw new RuntimeException("ArryQueue为空");
        }
        return queue[front];
    }
}
