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
        int i = 0;          //左下标
        int j = arr.length - 1; //右下标
        while (i <= j) {
            ++count;
            mid = (i + j) / 2;

            System.out.println("arr[" + i + "]=" + arr[i] + " arr[" + j + "]=" + arr[j] + " arr[" +
                    mid + "]=" + arr[mid]);

            //如果要找的数等于数组中间值，则返回索引
            if (arr[mid] == key) {
                return mid;
            }

            //要找的数比数组中间值小，则需要在数组左边查找
            else if (arr[mid] > key) {
                j = mid - 1;
            } else if (arr[mid] < key) {
                i = mid + 1;
            }
        }

        //如果找不到，返回-1
        return -1;
    }

    //查找第一个大于key的数组元素下标
    public static int biggerBinarySearch(int[] arr, int key) {
        int mid;
        int i = 0;          //左下标
        int j = arr.length - 1; //右下标
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


    //查找最后一个小于key的数组元素下标
    public static int smallerBinarySearch(int[] arr, int key) {
        int mid;
        int i = 0;              //左下标
        int j = arr.length - 1;   //右下标
        while (i <= j) {
            System.out.println(i + " " + j);
            mid = i + (j - i) >> 1;

            //如果
            if (key <= arr[mid]) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }

        }

        //如果找不到，返回-1
        return i - 1 >= 0 ? i - 1 : -1;
    }
}
