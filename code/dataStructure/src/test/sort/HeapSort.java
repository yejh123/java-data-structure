package test.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yejh
 * @create 2019-08_14 11:32
 * 堆排序
 * 时间复杂度#####
 * 堆排序是一种十分高效的排序算法，因为它的排序流程分三步，那么可以分别计算时间复杂度进行相加：
 * 1.构建堆：
 * 构建堆是从N/2处开始进行调整，每一次调整的时间复杂度为节点的深度H，那么N/2次调整则为O（H1）+O（H2）.....O(HnN/2)。由于H为常数，那么时间复杂度为O（N）
 * 2.调整堆：
 * 调整堆比较简单，由二叉堆具有堆序性质，那么调整堆的过程其实，就是堆的深度即lgN
 * 3.堆排序
 * 此过程是进行N-1次调整堆的操作，那么此过程的时间复杂为(N-1)lgN。
 * 汇总后整体的时间复杂度为O（N+(N-1)*lgN） ~ O(NlgN)
 * 可见堆排序的时间复杂度是比较低的，但是这种排序一般比较适合大数据集合的排序，因为大量使用了递归操作，那么在小数据集的情况下是十分消耗性能的，在小数据集的情况下最好使用插入、选择这种简单的排序算法，往往能起到更好的效果。
 * 对TopK问题的优势：
 * 堆排序另外一个比较好的特性就是TopK，因为堆排序是渐进排序，也就是说不是将所有的数据排序好后输出。这种特性也就决定了，在TopK场景往往能够有更好的表现。
 * 空间复杂度
 * 从上面程序可以看出来，我们并没有引入第二个存储元素，而每一次元素的交换仅仅依靠一个元素的存储空间，所以堆排序的空间复杂度为O（1）
 * 参考：
 * <p>
 * 作者：一只小哈
 * 链接：https://www.jianshu.com/p/1cfdcee48003
 * 来源：简书
 * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 *
 *
 *  * 数据规模     一万      十万      一百万         一千万         一亿
 *  * 时间          2        12        121           2052         38566
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
        //System.out.println("排序前 " + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        heapSort(arr);
        //System.out.println("排序后 " + Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        Date end = new Date();
        System.out.println(simpleDateFormat.format(end));
    }

    public static void heapSort(int[] arr) {
        int len = arr.length;

        //第一步，构建大顶堆
        //1.1 寻找完全二叉树中最后一个非叶子节点
        //由于最后一个节点下标为len-1，所以其父节点也就是最后一个非叶子节点，下标为len/2 - 1
        //1.2 由adjustHeap方法的使用前提，我们需要从右往左，从下往上调整大堆顶
        for (int i = len / 2 - 1; i >= 0; --i){
            adjustHeap(arr,i,len);
        }

        //System.out.println(Arrays.toString(arr));

        for(int j = len -1; j > 0; --j){
            //第二步，堆顶元素与末尾元素交换
            int temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;

            //第三步，调整堆
            //由于除了堆顶元素外，其他的树结构符合大堆顶，所以只需进行一次adjustHeap即可将整个树调整回大堆顶
            adjustHeap(arr,0,j);
        }
    }

    /* 调整以father（下标）为父节点的局部树为大堆顶
     * 使用该方法前提：father的子节点（一个或两个）同时也是各自大堆顶的父节点 ！！！
     *
     */
    private static void adjustHeap(int[] arr, int father, int len) {
        int temp = arr[father];
        for (int child = 2 * father + 1; child < len; child = 2 * child + 1) {
            if (child + 1 < len && arr[child] < arr[child + 1]) {
                child++;
            }
            //此时arr[i]是father的子节点中较大者
            //！！！注意比较的是arr[child]和temp，而不是arr[child]和arr[father]
            if (arr[child] > temp) {
                arr[father] = arr[child];
                father = child;
                //可以进入if语句，说明arr[father]比子节点小，甚至可能比孙节点小，所以进行下一个循环
            } else {
                //arr[father]比子节点大，不必进行下一个循环
                break;
            }
        }
        //将temp放到调整好的位置
        arr[father] = temp;
    }
}
