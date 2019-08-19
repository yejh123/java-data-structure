package test.sort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yejh
 * @create 2019-08_11 22:43
 *
 * 数据规模     一万      十万      一百万         一千万         一亿
 * 时间          2        12        114           1370         14471
 *
 */
public class MergeSort {
    public static void main(String[] args) {
        Date start = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(start));
        int[] arr = new int[1000000000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * arr.length);
        }
        //System.out.println("排序前 " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        mergeSort(arr);
        //System.out.println("排序后 " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        Date end = new Date();
        System.out.println(simpleDateFormat.format(end));
    }

    public static void mergeSort(int[] arr) {
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length - 1, temp);
    }

    private static void sort(int[] arr, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        sort(arr, left, mid, temp);
        sort(arr, mid + 1, right, temp);
        merge(arr, left, right, temp);
    }

    private static void merge(int[] arr, int left, int right, int[] temp) {
        int i = left;
        int mid = (left + right) / 2;
        int j = mid + 1;
        int t= 0;
        while (i <= mid && j <= right) {

            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while(j <= right){
            temp[t++] = arr[j++];
        }

        t = 0;
        while(left <= right) {
            arr[left++] = temp[t++];
        }
    }

}
