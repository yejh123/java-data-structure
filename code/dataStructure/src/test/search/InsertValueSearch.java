package test.search;

/**
 * @author yejh
 * @create 2019-08_12 12:07
 */
public class InsertValueSearch {
    public static int count = 0;

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int i = insertValueSearch(arr, 8);
        System.out.println(i);
    }

    public static int getCount() {
        return count;
    }

    public static int insertValueSearch(int[] arr, int key) {
        int mid;
        int i = 0;          //左下标
        int j = arr.length - 1; //右下标
        while (i <= j) {
            ++count;

            //注意初步判断key的范围，否则可能出现数组越界
            if (key < arr[i] || key > arr[j]) {
                return -1;
            }

            //防止除以零异常
            if (arr[i] == arr[j]) {
                if (arr[i] == key) {
                    return i;
                } else {
                    return -1;
                }
            }


            mid = (int)(i + (double)(key - arr[i]) / (arr[j] - arr[i]) * (j - i));

            System.out.println("arr[" + i + "]=" + arr[i] + " arr[" + j + "]=" + arr[j] + " arr[" +
                    mid + "]=" + arr[mid] + " key=" + key);

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
        return -1;
    }
}
