package com.example.myapplication.algorithm;

import android.util.Log;

/**
 * 八皇后问题
 */
public class EightQueen {
    public static final String TAG = EightQueen.class.toString();
    private int max = 8;
    private int[] arry;
    private int count = 0;
    private int judeCount = 0;

    public EightQueen(int max) {
        this.max = max;
        arry = new int[max];
    }

    /**
     * 放置第n个皇后判断是否符合条件
     * 1.不在同一列,arry[i]==arry[n]
     * 2.不在同一行,Math.abs(n-i)==Math.abs(arry[n]-arry[i]
     * @param n
     * @return 是否符合
     */
    public boolean jude(int n){
        judeCount++;
        for(int i = 0 ; i <n;i++ ){
            if(arry[i]==arry[n]||(Math.abs(n-i)==Math.abs(arry[n]-arry[i]))){
                return false;
            }
        }
        return true;
    }

    /**
     * 开始递归回溯所有可能
     * @param n 第几个皇后
     */
    public void check(int n){
        if(n==max){
            count++;
            Log.d(TAG, "check: this is the" + count);
            pritn();
        return;
    }
    for (int i=0;i<max;i++){
            arry[n] = i;
            if(jude(n)){
                check(n+1);
            }
    }
    }

    private void pritn(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<max;i++){
            sb.append(arry[i]+" ");
        }
        Log.d(TAG, "pritn: "+sb.toString());
    }
}
