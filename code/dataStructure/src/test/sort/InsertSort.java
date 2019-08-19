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

        //�����ĩβԪ�ص��±�
        int index;
        for (index = 0; index < len - 1; ++index) {
            //�����������ȡ��Ԫ�ذ�˳����뵽�������
            //�������Ԫ��
            int ele = arr[index + 1];

            //����������������
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
