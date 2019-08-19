package test.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author yejh
 * @create 2019-08_11 11:29
 */
public class InsertSort {
    public static void insertSort(int[] arr) {
        int len = arr.length;

        //有序表末尾元素的下标
        int index;
        for (index = 0; index < len - 1; ++index) {
            //将无序表中提取的元素按顺序插入到有序表中
            //待插入的元素
            int ele = arr[index + 1];

            //从右往左遍历有序表
            int i;
            for(i = index; i >= 0 && ele < arr[i]; --i) {

                arr[i + 1] = arr[i];
            }
            arr[++i] = ele;
        }
    }

    @Test
    public void insertSortTest() {
        int arr[] = new int[50];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * 100);
        }
        insertSort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
