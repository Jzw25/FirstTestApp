package com.example.myapplication.algorithm;

import android.util.Log;

/**
 * 递归
 * 自己调用自己
 */
public class RecursionTest {
    public static final String TAG = RecursionTest.class.toString();


    //递归输出
    public void simpolRecursion(int n){
        if(n>2){
            simpolRecursion(n-1);
        }
        Log.d(TAG, "simpolRecursion: "+n);
    }

    /**
     * 阶乘方法
     * @param n 输入数字
     */
    public int simpolFactorial(int n){
        if(n==1){
            return 1;
        }else {
            return simpolFactorial(n-1)*n;
        }
    }

    public int[][] setMap(){
        int[][] map = new int[8][7];
        for (int i = 0 ; i < 7;i++ ){
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int j = 1;j<8;j++){
            map[j][0] = 1;
            map[j][6] = 1;
        }
        map[2][1] = 1;
        map[2][2] = 1;
        return map;
    }

    /**
     * 迷宫问题
     * @param map 迷宫地图
     * @param i 坐标，从哪儿开始
     * @param j
     */
    public boolean miGong(int[][] map,int i,int j){
        //定义一个二维数组作为迷宫，数值1表示障碍，0表示通路，2表示已走过，3表示走过但是下一步不通
        if(map[6][5]==2){
            return true;
        }else if(map[i][j]==0){
            //先认为走得通，将该点置为2
            map[i][j] = 2;
            //接下来按照策略往下走，先下再右再上再左
            if(miGong(map,i,j+1)){
                return true;
            }else if(miGong(map,i+1,j)){
                return true;
            }else if(miGong(map,i,j-1)){
                return true;
            }else if(miGong(map,i-1,j)){
                return true;
            }else {
                map[i][j] = 3;
                return false;
            }
        }else {
            return false;
        }
    }
}
