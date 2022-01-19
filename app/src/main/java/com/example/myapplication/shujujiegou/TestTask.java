package com.example.myapplication.shujujiegou;

import android.util.Log;

public class TestTask<T> {
    private int[] task;
    private int crruent = 0;
    private int maxSize;
    public static final String TAG = "TestTask";

    public TestTask(int maxSize) {
        this.maxSize = maxSize;
        task = new int[maxSize];
    }

    public void add(int num) {
        if (crruent == maxSize) {
            Log.d(TAG, "add: " + "max");
        } else {
            task[crruent] = num;
            crruent++;
        }
    }

    public int pupOne() {
        if (crruent > 0) {
            crruent--;
            return task[crruent];
        }else {
            throw new RuntimeException("Task is Empty");
        }
    }

    public int getSize(){
        return crruent;
    }

    public int getTop() {
        return task[crruent - 1];
    }

    public void showAll(){
        for (int i = crruent - 1; i >= 0; i--) {
            Log.d(TAG, "showAll: " + task[i]);
        }
    }

    public void pupAll() {
        for (int i = crruent - 1; i >= 0; i--) {
            Log.d(TAG, "showAll: " + task[i]);
        }
        crruent = 0;
    }
}
