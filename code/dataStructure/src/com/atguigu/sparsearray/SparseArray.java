package com.atguigu.sparsearray;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class SparseArray {

    public static void main(String[] args) {
        // 创建一个原始的二维数组 11 * 11
        // 0: 表示没有棋子， 1 表示 黑子 2 表蓝子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;

        // 输出原始的二维数组
        System.out.println("得到原始二维数组为~~~~");
        print2DArr(chessArr1);

        //二维数组转稀疏数组
        int[][] sparseArr = chessArr_to_sparseArr(chessArr1);

        //文件存储稀疏数组
        storeSparseArr(sparseArr);

        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为~~~~");
        print2DArr(sparseArr);

        //二维数组转稀疏数组
        int[][] chessArr2 = sparseArr_to_chessArr(sparseArr);

        // 输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组~~~~");
        print2DArr(chessArr2);

        int[][] sparseArr2 = readSparseArr();
        System.out.println("读取文件得到的二维数组~~~~");
        print2DArr(sparseArr2);
    }

    public static int[][] chessArr_to_sparseArr(int[][] chessArr) {
        // 将二维数组 转 稀疏数组
        // 1. 先遍历二维数组 得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 2. 创建对应的稀疏数组
        int sparseArr[][] = new int[sum + 1][3];
        // 给稀疏数组赋值
        sparseArr[0][0] = chessArr.length;        //行数
        sparseArr[0][1] = chessArr[0].length;    //列数
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存放到 sparseArr中
        int count = 0; //count 用于记录是第几个非0数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (count == sum) {
                    break;
                }
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
            if (count == sum) {
                break;
            }
        }
        return sparseArr;
    }

    public static void print2DArr(int[][] arr) {
        for (int[] row : arr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }

    public static int[][] sparseArr_to_chessArr(int[][] sparseArr) {
        //将稀疏数组 --》 恢复成 原始的二维数组
		/*
		 *  1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
			2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
		 */

        //1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组

        int chessArr[][] = new int[sparseArr[0][0]][sparseArr[0][1]];

        //2. 在读取稀疏数组后几行的数据(从第二行开始)，并赋给 原始的二维数组 即可

        for (int i = 1; i < sparseArr.length; i++) {
            chessArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        return chessArr;
    }


    public static void storeSparseArr(int[][] sparseArr) {
        if (sparseArr[0].length != 3) {
            System.out.println("传入数组不是稀疏数组");
            return;
        }
        File file = new File("dataStructure\\src\\com\\atguigu\\sparsearray\\sparsearray.txt");
        System.out.println(file.getAbsolutePath());

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);

            for (int[] arr : sparseArr) {
                printWriter.println(arr[0] + " " + arr[1] + " " + arr[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public static int[][] readSparseArr() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("dataStructure\\src\\com\\atguigu\\sparsearray\\sparsearray.txt"));
            //先确定文件有多少行
            int count = 0;
            while (scanner.hasNext()) {
                scanner.nextLine();
                ++count;
            }

            int[][] sparseArr = new int[count][3];

            scanner = new Scanner(new File("dataStructure\\src\\com\\atguigu\\sparsearray\\sparsearray.txt"));

            for (int i = 0; i < count; ++i) {
                sparseArr[i][0] = scanner.nextInt();
                sparseArr[i][1] = scanner.nextInt();
                sparseArr[i][2] = scanner.nextInt();
            }
            return sparseArr;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(scanner != null){
                scanner.close();
            }
        }
        return null;
    }

}
