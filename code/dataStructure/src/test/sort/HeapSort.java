package test.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yejh
 * @create 2019-08_14 11:32
 * ������
 * ʱ�临�Ӷ�#####
 * ��������һ��ʮ�ָ�Ч�������㷨����Ϊ�����������̷���������ô���Էֱ����ʱ�临�ӶȽ�����ӣ�
 * 1.�����ѣ�
 * �������Ǵ�N/2����ʼ���е�����ÿһ�ε�����ʱ�临�Ӷ�Ϊ�ڵ�����H����ôN/2�ε�����ΪO��H1��+O��H2��.....O(HnN/2)������HΪ��������ôʱ�临�Ӷ�ΪO��N��
 * 2.�����ѣ�
 * �����ѱȽϼ򵥣��ɶ���Ѿ��ж������ʣ���ô�����ѵĹ�����ʵ�����Ƕѵ���ȼ�lgN
 * 3.������
 * �˹����ǽ���N-1�ε����ѵĲ�������ô�˹��̵�ʱ�临��Ϊ(N-1)lgN��
 * ���ܺ������ʱ�临�Ӷ�ΪO��N+(N-1)*lgN�� ~ O(NlgN)
 * �ɼ��������ʱ�临�Ӷ��ǱȽϵ͵ģ�������������һ��Ƚ��ʺϴ����ݼ��ϵ�������Ϊ����ʹ���˵ݹ��������ô��С���ݼ����������ʮ���������ܵģ���С���ݼ�����������ʹ�ò��롢ѡ�����ּ򵥵������㷨���������𵽸��õ�Ч����
 * ��TopK��������ƣ�
 * ����������һ���ȽϺõ����Ծ���TopK����Ϊ�������ǽ�������Ҳ����˵���ǽ����е���������ú��������������Ҳ�;����ˣ���TopK���������ܹ��и��õı��֡�
 * �ռ临�Ӷ�
 * �����������Կ����������ǲ�û������ڶ����洢Ԫ�أ���ÿһ��Ԫ�صĽ�����������һ��Ԫ�صĴ洢�ռ䣬���Զ�����Ŀռ临�Ӷ�ΪO��1��
 * �ο���
 * <p>
 * ���ߣ�һֻС��
 * ���ӣ�https://www.jianshu.com/p/1cfdcee48003
 * ��Դ������
 * ��������Ȩ���������У��κ���ʽ��ת�ض�����ϵ���߻����Ȩ��ע��������
 *
 *
 *  * ���ݹ�ģ     һ��      ʮ��      һ����         һǧ��         һ��
 *  * ʱ��          2        12        121           2052         38566
 *
 */
public class HeapSort {
    public static void main(String[] args) {
        Date start = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(start));
        int[] arr = new int[1000000000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * arr.length);
        }
        //System.out.println("����ǰ " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        heapSort(arr);
        //System.out.println("����� " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        Date end = new Date();
        System.out.println(simpleDateFormat.format(end));
    }

    public static void heapSort(int[] arr) {
        int len = arr.length;

        //��һ���������󶥶�
        //1.1 Ѱ����ȫ�����������һ����Ҷ�ӽڵ�
        //�������һ���ڵ��±�Ϊlen-1�������丸�ڵ�Ҳ�������һ����Ҷ�ӽڵ㣬�±�Ϊlen/2 - 1
        //1.2 ��adjustHeap������ʹ��ǰ�ᣬ������Ҫ�������󣬴������ϵ�����Ѷ�
        for (int i = len / 2 - 1; i >= 0; --i){
            adjustHeap(arr,i,len);
        }

        //System.out.println(Arrays.toString(arr));

        for(int j = len -1; j > 0; --j){
            //�ڶ������Ѷ�Ԫ����ĩβԪ�ؽ���
            int temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;

            //��������������
            //���ڳ��˶Ѷ�Ԫ���⣬���������ṹ���ϴ�Ѷ�������ֻ�����һ��adjustHeap���ɽ������������ش�Ѷ�
            adjustHeap(arr,0,j);
        }
    }

    /* ������father���±꣩Ϊ���ڵ�ľֲ���Ϊ��Ѷ�
     * ʹ�ø÷���ǰ�᣺father���ӽڵ㣨һ����������ͬʱҲ�Ǹ��Դ�Ѷ��ĸ��ڵ� ������
     *
     */
    private static void adjustHeap(int[] arr, int father, int len) {
        int temp = arr[father];
        for (int child = 2 * father + 1; child < len; child = 2 * child + 1) {
            if (child + 1 < len && arr[child] < arr[child + 1]) {
                child++;
            }
            //��ʱarr[i]��father���ӽڵ��нϴ���
            //������ע��Ƚϵ���arr[child]��temp��������arr[child]��arr[father]
            if (arr[child] > temp) {
                arr[father] = arr[child];
                father = child;
                //���Խ���if��䣬˵��arr[father]���ӽڵ�С���������ܱ���ڵ�С�����Խ�����һ��ѭ��
            } else {
                //arr[father]���ӽڵ�󣬲��ؽ�����һ��ѭ��
                break;
            }
        }
        //��temp�ŵ������õ�λ��
        arr[father] = temp;
    }
}
