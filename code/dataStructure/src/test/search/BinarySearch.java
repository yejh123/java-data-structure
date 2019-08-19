package test.search;

import java.util.List;

/**
 * @author yejh
 * @create 2019-08_12 10:41
 */
public class BinarySearch {
    public static int count = 0;
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};


        //
//		int resIndex = binarySearch(arr, 0, arr.length - 1, 1000);
//		System.out.println("resIndex=" + resIndex);

        int i = biggerBinarySearch(arr, -1);
        System.out.println(i);
    }

    public static int getCount(){
        return count;
    }
    public static int equalsBinarySearch(int[] arr, int key) {
        int mid;
        int i = 0;          //���±�
        int j = arr.length - 1; //���±�
        while (i <= j) {
            ++count;
            mid = (i + j) / 2;

            System.out.println("arr[" + i + "]=" + arr[i] + " arr[" + j + "]=" + arr[j] + " arr[" +
                    mid + "]=" + arr[mid]);

            //���Ҫ�ҵ������������м�ֵ���򷵻�����
            if (arr[mid] == key) {
                return mid;
            }

            //Ҫ�ҵ����������м�ֵС������Ҫ��������߲���
            else if (arr[mid] > key) {
                j = mid - 1;
            } else if (arr[mid] < key) {
                i = mid + 1;
            }
        }

        //����Ҳ���������-1
        return -1;
    }

    //���ҵ�һ������key������Ԫ���±�
    public static int biggerBinarySearch(int[] arr, int key) {
        int mid;
        int i = 0;          //���±�
        int j = arr.length - 1; //���±�
        while (i <= j) {
            System.out.println(i + " " + j);
            mid = i + (j - i) >> 1;

            if (key < arr[mid]) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }

        }

        return i <= arr.length ? i : -1;
    }


    //�������һ��С��key������Ԫ���±�
    public static int smallerBinarySearch(int[] arr, int key) {
        int mid;
        int i = 0;              //���±�
        int j = arr.length - 1;   //���±�
        while (i <= j) {
            System.out.println(i + " " + j);
            mid = i + (j - i) >> 1;

            //���
            if (key <= arr[mid]) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }

        }

        //����Ҳ���������-1
        return i - 1 >= 0 ? i - 1 : -1;
    }
}
