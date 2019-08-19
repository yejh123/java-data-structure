package com.atguigu.sparsearray;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class SparseArray {

    public static void main(String[] args) {
        // ����һ��ԭʼ�Ķ�ά���� 11 * 11
        // 0: ��ʾû�����ӣ� 1 ��ʾ ���� 2 ������
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;

        // ���ԭʼ�Ķ�ά����
        System.out.println("�õ�ԭʼ��ά����Ϊ~~~~");
        print2DArr(chessArr1);

        //��ά����תϡ������
        int[][] sparseArr = chessArr_to_sparseArr(chessArr1);

        //�ļ��洢ϡ������
        storeSparseArr(sparseArr);

        // ���ϡ���������ʽ
        System.out.println();
        System.out.println("�õ�ϡ������Ϊ~~~~");
        print2DArr(sparseArr);

        //��ά����תϡ������
        int[][] chessArr2 = sparseArr_to_chessArr(sparseArr);

        // ����ָ���Ķ�ά����
        System.out.println();
        System.out.println("�ָ���Ķ�ά����~~~~");
        print2DArr(chessArr2);

        int[][] sparseArr2 = readSparseArr();
        System.out.println("��ȡ�ļ��õ��Ķ�ά����~~~~");
        print2DArr(sparseArr2);
    }

    public static int[][] chessArr_to_sparseArr(int[][] chessArr) {
        // ����ά���� ת ϡ������
        // 1. �ȱ�����ά���� �õ���0���ݵĸ���
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 2. ������Ӧ��ϡ������
        int sparseArr[][] = new int[sum + 1][3];
        // ��ϡ�����鸳ֵ
        sparseArr[0][0] = chessArr.length;        //����
        sparseArr[0][1] = chessArr[0].length;    //����
        sparseArr[0][2] = sum;

        // ������ά���飬����0��ֵ��ŵ� sparseArr��
        int count = 0; //count ���ڼ�¼�ǵڼ�����0����
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
        //��ϡ������ --�� �ָ��� ԭʼ�Ķ�ά����
		/*
		 *  1. �ȶ�ȡϡ������ĵ�һ�У����ݵ�һ�е����ݣ�����ԭʼ�Ķ�ά���飬���������  chessArr2 = int [11][11]
			2. �ڶ�ȡϡ��������е����ݣ������� ԭʼ�Ķ�ά���� ����.
		 */

        //1. �ȶ�ȡϡ������ĵ�һ�У����ݵ�һ�е����ݣ�����ԭʼ�Ķ�ά����

        int chessArr[][] = new int[sparseArr[0][0]][sparseArr[0][1]];

        //2. �ڶ�ȡϡ��������е�����(�ӵڶ��п�ʼ)�������� ԭʼ�Ķ�ά���� ����

        for (int i = 1; i < sparseArr.length; i++) {
            chessArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        return chessArr;
    }


    public static void storeSparseArr(int[][] sparseArr) {
        if (sparseArr[0].length != 3) {
            System.out.println("�������鲻��ϡ������");
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
            //��ȷ���ļ��ж�����
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
