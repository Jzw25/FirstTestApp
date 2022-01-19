package com.example.myapplication.shujujiegou;

/**
 * 稀疏数组:矩阵存储
 * 需要一个数据+1行得3列得二维数组。第一列数据存放原始数据有多少行列和有数据得数量，从第二行开始记录有数据得行列+数据；
 */
public class SparseArry {
    private int[][] sparse;//稀疏数组
    private int count;//计数
    private int sum;//有数据得个数
    private int[] hang;//记录行
    private int[] lie;//记录列
    private int[] num;//记录数值

    public SparseArry(int[][] originalArry){
        changeToSparseArry(originalArry);
    }

    public void changeToSparseArry(int[][] originalArry){
        count = originalArry.length*originalArry[0].length;
        sum = 0;
        hang = new int[count];
        lie = new int[count];
        num = new int[count];
        for (int i = 0;i<originalArry.length;i++){
            for (int j = 0 ; j<originalArry[0].length;j++){
                if(originalArry[i][j]!=0){
                    hang[sum] = i;
                    lie[sum] = j;
                    num[sum] = originalArry[i][j];
                    sum++;
                }
            }
        }
        sparse = new int[sum+1][3];
        sparse[0][0] = originalArry.length;
        sparse[0][1] = originalArry[0].length;
        sparse[0][2] = sum;
        for (int k = 1;k<sum;k++){
            sparse[k][0] = hang[k-1];
            sparse[k][1] = lie[k-1];
            sparse[k][2] = num[k-1];
        }
    }

    //获取稀疏数组
    public int[][] getSparse(){
        return sparse;
    }

    //反编译原来数组
    public int[][] returnOriginalArry(){
        int[][] originalArry = new int[sparse[0][0]][sparse[0][1]];
        for (int i = 1;i<sparse.length;i++){
            originalArry[sparse[i][0]][sparse[i][1]] = sparse[i][2];
        }
        return originalArry;
    }
}
