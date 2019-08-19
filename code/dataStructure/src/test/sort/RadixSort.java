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
        //System.out.println("排序前 " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        radixSort(arr);
        //System.out.println("排序后 " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public static void radixSort(int[] arr) {
        //第一步，确定最大位数
        int max = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        int maxLength = ("" + max).length();

        //第二步，从个位到最高位一次进行桶排序
        //使用二维数组来模拟桶
        int[][] bucket = new int[10][arr.length];

        for (int i = 0, n = 1; i < maxLength; ++i, n *= 10) {
            //使用一个数组来记录每个桶的有效元素个数
            int[] bucketElementCounts = new int[10];
            //2.1 取出对应位置的数字，并按规则将数字放入桶中
            for (int t = 0; t < arr.length; ++t) {
                int digitOfElement = (arr[t] / n) % 10;
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[t];
                ++bucketElementCounts[digitOfElement];
            }

            //2.2 将桶中的数字按规则放入数组中
            int q = 0;
            for(int t = 0; t < 10; ++t){
                for(int p= 0; p < bucketElementCounts[t]; ++p){
                    arr[q++] = bucket[t][p];
                }
            }
        }
    }
}
