package test.sort;

import java.util.Arrays;

/**
 * @author yejh
 * @create 2019-08_11 21:18
 * <p>
 * �������򡪡�����ȡ�з�
 * https://www.cnblogs.com/chengxiao/p/6262208.html
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[300000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * 20);
        }
        //System.out.println("����ǰ " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        quickSort(arr, 0, arr.length - 1);
        //System.out.println("����� " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        //��ȡ��Ŧֵ����������ڵ�ǰ����������ĩβ
        dealPivot(arr, left, right);
        //��Ŧֵ����������ĩβ
        int pivot = arr[right - 1];
        int i = left;  //��ָ��
        int j = right-1;  //��ָ��

        while (i != j) {
            //������Ŧֵ���ң�����Ӧ�ô�������߿�ʼѰ�ҵ�һ��������Ŧֵ�����ֵ��±�
            while (arr[i] <= pivot && i != j) {
                ++i;
            }
            while (arr[j] >= pivot && i != j) {
                --j;
            }
            if (i != j) {
                swap(arr, i, j);
            }
        }
        if (i != right - 1) {
            swap(arr, i, right - 1);
        }
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    private static void dealPivot(int[] arr, int left, int right) {
        int mid = (left + right) / 2;

        if (arr[left] > arr[mid]) {
            swap(arr, left, mid);
        }
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[mid] > arr[right]) {
            swap(arr, mid, right);
        }
        //����λ�����õ����鵹���ڶ�λ
        swap(arr, mid, right - 1);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
