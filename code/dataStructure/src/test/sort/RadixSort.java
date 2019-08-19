package test.sort;

import java.util.Arrays;

/**
 * @author yejh
 * @create 2019-08_11 23:28
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = new int[10000000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * arr.length);
        }
        //System.out.println("����ǰ " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        radixSort(arr);
        //System.out.println("����� " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public static void radixSort(int[] arr) {
        //��һ����ȷ�����λ��
        int max = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        int maxLength = ("" + max).length();

        //�ڶ������Ӹ�λ�����λһ�ν���Ͱ����
        //ʹ�ö�ά������ģ��Ͱ
        int[][] bucket = new int[10][arr.length];

        for (int i = 0, n = 1; i < maxLength; ++i, n *= 10) {
            //ʹ��һ����������¼ÿ��Ͱ����ЧԪ�ظ���
            int[] bucketElementCounts = new int[10];
            //2.1 ȡ����Ӧλ�õ����֣������������ַ���Ͱ��
            for (int t = 0; t < arr.length; ++t) {
                int digitOfElement = (arr[t] / n) % 10;
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[t];
                ++bucketElementCounts[digitOfElement];
            }

            //2.2 ��Ͱ�е����ְ��������������
            int q = 0;
            for(int t = 0; t < 10; ++t){
                for(int p= 0; p < bucketElementCounts[t]; ++p){
                    arr[q++] = bucket[t][p];
                }
            }
        }
    }
}
