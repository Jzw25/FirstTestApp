package com.example.myapplication.algorithm;

import android.util.Log;

import java.util.Arrays;

/**
 * 算法工具类
 */
public class AlgorithmUtils {

    public static final String TAG = AlgorithmUtils.class.toString();

    /**
     * 冒泡排序
     * 将数据前一个与后一个进行对比，大的后移，最终无变化时输出，用一个flag表示如果一趟无交换，
     * 即可输出最终排序结果
     *
     * @param arr 源数据
     */
    public static void bubblingSort(int arr[]) {
        int temp;
        boolean isRight = false;
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isRight = true;
                }
            }
            if (!isRight) {
                break;
            } else {
                isRight = false;
            }
        }

        Log.d(TAG, "bubblingSort: the last arr is " + Arrays.toString(arr));
    }

    /**
     * 选择排序
     * 定义：每次取这段数组中的最小数（或者最大数）将其与第一个数交换，然后刨除这个数，
     * 再剩下的数中再次选取最小值（最大值）,直到排序完成
     * if arr[0]-arr[n] 中最小为arr[3],然后将arr[3]与arr[0]交换，然后在arr[1]-arr[n]之间取最小值与arr[1]交换,
     * 以此类推
     *
     * @param arr
     */
    public static void selectSort(int arr[]) {
        int min;
        int index;
        for (int i = 0; i < arr.length - 1; i++) {
            min = arr[i];
            index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    index = j;
                }
            }
            if (arr[i] != min) {
                arr[index] = arr[i];
                arr[i] = min;
            }
        }

        Log.d(TAG, "selectSort: the last sort is " + Arrays.toString(arr));
    }

    /**
     * 插入排序
     * 方法：将数组分为两部分，前段是排序完的，后段是乱序的，每次从后端乱序的头一个取出插入前段有序
     * 选择合适位置
     * 如：{3,1,7,5,9,10}-{3},{1,7,5,9,10}-{1,3},{7,5,9,10}....
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int temp;
        boolean isSet = false;
        frist:
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            second:
            for (int j = i - 1; j >= 0; j--) {
                if (temp < arr[j]) {
                    arr[j + 1] = arr[j];
                } else {
                    arr[j + 1] = temp;
                    isSet = true;
                    break second;//跳出循环如果想内层跳出外层可以给循环命名，此处指跳出内层名不命名无所谓
                }
            }
            if (isSet) {
                isSet = false;
            } else {
                arr[0] = temp;
            }
        }
        Log.d(TAG, "insertSort: the last sort is " + Arrays.toString(arr));
    }

    /**
     * 插入排序之while循环法
     *
     * @param arr 源数据
     */
    public static void insetSortWhile(int arr[]) {
        int temp;//记录需要插入的数据
        int index;//需要插入的位置
        for (int i = 1; i < arr.length; i++) {
            index = i;
            temp = arr[i];
            while (index > 0 && temp < arr[index - 1]) {
                index--;
                arr[index] = arr[index - 1];
            }
            arr[index] = temp;
        }

        Log.d(TAG, "insetSortWhile: the last sort is "+Arrays.toString(arr));

    }

    /**
     * 插入排序while循环优化版
     */
    public static void insertSortWhilePerfert(int arr[]) {
        int temp;//记录需要插入的数据
        int index;//需要插入的位置
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                index = i;
                temp = arr[i];
                while (index > 0 && temp < arr[index - 1]) {
                    index--;
                    arr[index] = arr[index - 1];
                }
                arr[index] = temp;
            }
        }
        Log.d(TAG, "insertSortWhilePerfert: the last sort is "+Arrays.toString(arr));
    }

    /**
     * 希尔排序
     * 定义:将一组数据分割成lengths/2组，然后每组之内进行插入排序，然后再分割成lengths/2/2组，进行相同的操作，
     * 直到分成最后一组，再进行插入排序。
     * 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率。希尔排序就是对插入排序的不稳定优化
     */
    public static void shellSort(int arr[]) {
        int group = arr.length;
        int temp;
        int index;
        while (group!=1){
            group/=2;
            for (int i = 0;i<group;i++){
                for(int j = i ; j<arr.length-group;j+=group){
                    index = j;
                    while (index>=0&&arr[index]>arr[index+group]){
                        temp = arr[index];
                        arr[index] = arr[index+group];
                        arr[index+group] = temp;
                        index-=group;
                    }

                }
            }
        }

        Log.d(TAG, "shellSort: the last sort is "+ Arrays.toString(arr));
    }

    /**
     * 希尔排序，插入排序法（移位法）
     */
    public static void shellSortNotChange(int[] arr){
        int temp;
        int index;
        for (int group = arr.length/2;group>0;group/=2){
            for (int i = group;i<arr.length;i++){
                index = i;
                temp = arr[index];
                if(arr[index]<arr[index-group]){
                    while (index>=0&&arr[index]<arr[index-group]){
                        arr[index] = arr[index-group];
                        index-=group;
                    }
                    arr[index] = temp;
                }
            }
        }

        Log.d(TAG, "shellSortNotChange: the lase sort is " + Arrays.toString(arr));
    }

    /**
     * 快速排序法（冒泡排序法的优化）
     * 定义：将一组数据以中间数分割，小于此数的去前部数组中，大于此数的去后部数组中，第二次就以这两个数组中的数再来一次，
     * 即可以用递归方法
     * 例：{-9，78，0，256，3，-56} -> {-9,-56,0,256,3,78} ->{-9,-56,0,3,256,78}
     * 实际{-9,-56}0{256,3,78}->{-9,-56}0{}3{256,78}
     */

    public static void quickSort(int arr[],int left,int right){
        int l = left;
        int r = right;
        int temp;
        int current = arr[(left+right)/2];
        while (l<r){
            while (arr[l]<current){
                l++;
            }

            while (arr[r]>current){
                r--;
            }

            if(l>=r){
                break;
            }
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //因一边已经到达中间，防止无线循环
            if(arr[l] == current){
                r--;
            }

            if(arr[r]==current){
                l++;
            }

            //必须如此，否则栈溢出
            if(l==r){
                l++;
                r--;
            }

            //向左递归
            if(left<r){
                quickSort(arr,left,r);
            }

            //向右递归

            if(right>l){
                quickSort(arr,l,right);
            }

        }
    }

    /**
     * 归并排序
     * 定义：将一组数据分开拆之后再合并,图解再res/suanfa下
     */

    public static void mergeSort(int arr[],int left,int right,int[] temp){
        if(left<right){
            int mid = (left+right)/2;
            //向左递归分解
            mergeSort(arr,left,mid,temp);
            //向右递归分解
            mergeSort(arr,mid+1,right,temp);
            //合并
            merge(arr,left,right,mid,temp);
        }
    }

    //合并方法
    public static void merge(int arr[],int left,int mid,int right ,int[] temps){
        int l = left;
        int r = mid;
        int temp = 0;

        while (l<=mid && r<=right){
            if(arr[l]<=arr[r]){
                temps[temp] = arr[l];
                temp++;
                l++;
            }else {
                temps[temp] = arr[r];
                temp++;
                r++;
            }
        }

        //如果一边没有完全放完
        while (l<=mid){
            temps[temp] = arr[l];
            temp++;
            l++;
        }

        while (r<=right){
            temps[temp] = arr[r];
            temp++;
            r++;
        }

        //将数组拷贝至arr
        temp = 0;
        int tempLeft = left;
        while (tempLeft<=right){
            arr[tempLeft] = temps[temp];
            temp++;
            tempLeft++;
        }
    }
}
