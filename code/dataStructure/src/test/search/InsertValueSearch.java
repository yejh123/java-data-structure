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
        int i = 0;          //���±�
        int j = arr.length - 1; //���±�
        while (i <= j) {
            ++count;

            //ע������ж�key�ķ�Χ��������ܳ�������Խ��
            if (key < arr[i] || key > arr[j]) {
                return -1;
            }

            //��ֹ�������쳣
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
        return -1;
    }
}
