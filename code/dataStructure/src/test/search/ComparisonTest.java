package test.search;

import test.sort.MergeSort;

/**
 * @author yejh
 * @create 2019-08_12 13:22
 *
 * 运行下列程序，可以看到无论数组多大，二分查找和插值查找所花费时间一般不超过2毫秒。
 * 考虑到二分查找的可读性和简洁性，建议以后少编写插值查找
 *
 * 创建数组所花时间：2894
 * 排序所花时间：22323
 * 要查找的值：39644712
 *
 * arr[0]=0 arr[149999999]=149999998 arr[74999999]=74997330
 * arr[0]=0 arr[74999998]=74997329 arr[37499999]=37500588
 * arr[37500000]=37500589 arr[74999998]=74997329 arr[56249999]=56250915
 * arr[37500000]=37500589 arr[56249998]=56250915 arr[46874999]=46868263
 * arr[37500000]=37500589 arr[46874998]=46868261 arr[42187499]=42181596
 * arr[37500000]=37500589 arr[42187498]=42181595 arr[39843749]=39841786
 * arr[37500000]=37500589 arr[39843748]=39841786 arr[38671874]=38671434
 * arr[38671875]=38671436 arr[39843748]=39841786 arr[39257811]=39256750
 * arr[39257812]=39256750 arr[39843748]=39841786 arr[39550780]=39549176
 * arr[39550781]=39549180 arr[39843748]=39841786 arr[39697264]=39695359
 * arr[39550781]=39549180 arr[39697263]=39695359 arr[39624022]=39621879
 * arr[39624023]=39621879 arr[39697263]=39695359 arr[39660643]=39658641
 * arr[39624023]=39621879 arr[39660642]=39658641 arr[39642332]=39640135
 * arr[39642333]=39640136 arr[39660642]=39658641 arr[39651487]=39649369
 * arr[39642333]=39640136 arr[39651486]=39649368 arr[39646909]=39644816
 * arr[39642333]=39640136 arr[39646908]=39644815 arr[39644620]=39642553
 * arr[39644621]=39642553 arr[39646908]=39644815 arr[39645764]=39643692
 * arr[39645765]=39643695 arr[39646908]=39644815 arr[39646336]=39644271
 * arr[39646337]=39644272 arr[39646908]=39644815 arr[39646622]=39644548
 * arr[39646623]=39644551 arr[39646908]=39644815 arr[39646765]=39644677
 * arr[39646766]=39644678 arr[39646908]=39644815 arr[39646837]=39644748
 * arr[39646766]=39644678 arr[39646836]=39644747 arr[39646801]=39644712
 * 二分查找：arr[39646801]=39644712 所花时间：1 总循环次数：22
 *
 * arr[0]=0 arr[149999999]=149999998 arr[39644712]=39642637 key=39644712
 * arr[39644713]=39642643 arr[149999999]=149999998 arr[39646781]=39644693 key=39644712
 * arr[39646782]=39644695 arr[149999999]=149999998 arr[39646798]=39644709 key=39644712
 * arr[39646799]=39644710 arr[149999999]=149999998 arr[39646800]=39644710 key=39644712
 * arr[39646801]=39644712 arr[149999999]=149999998 arr[39646801]=39644712 key=39644712
 * 插值查找：arr[39646801]=39644712 所花时间：1 总循环次数：5
 */
public class ComparisonTest {
    public static void main(String[] args) {
        long createStart = System.currentTimeMillis();
        int[] arr = new int[150000000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * arr.length);
        }
        long createEnd = System.currentTimeMillis();
        System.out.println("创建数组所花时间：" + (createEnd - createStart));

        long sortStart = System.currentTimeMillis();
        MergeSort.mergeSort(arr);
        long sortEnd = System.currentTimeMillis();
        System.out.println("排序所花时间：" + (sortEnd - sortStart));

        int a = (int) (Math.random() * arr.length);
        //System.out.println("需要查找：" + a);

        long findStart1 = System.currentTimeMillis();
        int index1 = BinarySearch.equalsBinarySearch(arr, a);
        long findEnd1 = System.currentTimeMillis();
        System.out.println("二分查找：" + "arr[" + index1 + "]=" + (index1 > -1 ? arr[index1] : "找不到") +
                " 所花时间：" + (findEnd1 - findStart1) + " 总循环次数：" + BinarySearch.getCount() + "\n");

        long findStart2 = System.currentTimeMillis();
        int index2 = InsertValueSearch.insertValueSearch(arr, a);
        long findEnd2 = System.currentTimeMillis();
        System.out.println("插值查找：" + "arr[" + index2 + "]=" + (index2 > -1 ? arr[index2] : "找不到") +
                " 所花时间：" + (findEnd2 - findStart2) + " 总循环次数：" + InsertValueSearch.getCount());
    }
}
