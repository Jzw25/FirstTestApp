package com.example.myapplication.shujujiegou;

import android.util.Log;

public class StringTask {
    private String[] task;
    private int current = -1;
    private int maxSize;
    public static final String TAG = StringTask.class.toString();

    public StringTask(int maxSize){
        this.maxSize = maxSize;
        task = new String[maxSize];
    }

    public boolean isEmpty(){
        return current==-1;
    }

    public boolean isFull(){
        return current == maxSize-1;
    }

    public void add(String s){
        if(isFull()){
            Log.d(TAG, "add: "+"task is full");
            return;
        }
        current++;
        task[current] = s;
    }

    public String del(){
        if(isEmpty()){
            throw new RuntimeException("task is empty!");
        }
        String s = task[current];
        current--;
        return s;
    }

    public String getTop(){
        if(isEmpty()){
            throw new RuntimeException("task is empty!");
        }
        return task[current];
    }

    public int getSize(){
        return current+1;
    }

    public void showAll(){
        int count = current;
        while (count!=-1){
            Log.d(TAG, "showAll: "+task[count]);
            count--;
        }
    }
}
