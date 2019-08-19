package test.dynamic;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author yejh
 * @create 2019-08_19 10:55
 */
public class KnapsackProblem {
    /* 背包问题解决方法
     * weight：每个物品的重量
     * value：每个物品的价值
     * maxWeight：背包承受的最大重量
     */
    public int solve(int[] weight, int[] value, int maxWeight) {
        if (weight.length != value.length) {
            throw new RuntimeException("传入的weight数组和value数组大小不一致");
        }
        int len = weight.length;

        //v用来表示在前row个物品中能够装入容量为column的背包中的最大价值
        int[][] v = new int[len + 1][maxWeight + 1];
        //path用来记录实现最大价值时选择的物品
        boolean[][] path = new boolean[len + 1][maxWeight + 1];

        //第一步，将v的第一行和第一列置为0
        for (int i = 0; i <= len; ++i) {
            v[i][0] = 0;
        }
        for (int i = 0; i <= maxWeight; ++i) {
            v[0][i] = 0;
        }

        //第二步，补全v
        //row表示第row个物品
        //column表示背包所能承受的最大重量
        for (int row = 1; row <= len; ++row) {
            for (int column = 1; column <= maxWeight; ++column) {
                if (column < weight[row - 1]) {
                    v[row][column] = v[row - 1][column];
                } else {
                    //v[row][column] = Math.max(v[row - 1][column], value[row-1] + v[row - 1][column - weight[row-1]]);
                    if (v[row - 1][column] < value[row - 1] + v[row - 1][column - weight[row - 1]]) {
                        v[row][column] = value[row - 1] + v[row - 1][column - weight[row - 1]];
                        //给path对应的位置作个标记
                        path[row][column] = true;
                    }
                }
            }
        }

        //第三步，输出v表格
        for (int[] arr : v) {
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("装入策略：");

        //第四步，输出实现最大价值时选择的物品
        int i = len;
        int j = maxWeight;
        while (i > 0 && j > 0) {
            if(path[i][j] == true){
                System.out.println("把第" + i +"个物品（重量" + weight[i-1] + "）放入背包");
                j -= weight[i-1];
            }
            --i;
        }
        return v[len][maxWeight];
    }

    @Test
    public void test() {
        int[] weight = {1, 4, 3};//物品的重量
        int[] value = {1500, 3000, 2000}; //物品的价值 这里val[i] 就是前面讲的v[i]
        int maxWeight = 4; //背包的容量

        solve(weight, value, maxWeight);
    }
}
