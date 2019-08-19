package test.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yejh
 * @create 2019-08_12 22:58
 */
public class ShellSort {
    public static void main(String[] args) {
        Date start = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(start));
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * arr.length);
        }
        System.out.println("排序前 " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        shellSort(arr);
        System.out.println("排序后 " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        Date end = new Date();
        System.out.println(simpleDateFormat.format(end));
    }

    public static void shellSort(int[] arr){
        int len = arr.length;
        int gap;

        for(gap = len/2; gap > 0; gap /= 2){
            for(int i = gap; i < len; ++i ){
                //分组进行插入排序
                int t = i;
                int temp = arr[i];
                while(t - gap >= 0 && arr[t-gap] > temp){
                    arr[t] = arr[t-gap];
                    t -= gap;
                }
                //System.out.println(arr[t] + " " + temp);
                //这时下标为t的位置应该恰好是arr[i]的插入位置
                arr[t] = temp;
            }
            //System.out.println( Arrays.toString(arr));
        }
    }
}
