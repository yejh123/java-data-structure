package test.dynamic;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author yejh
 * @create 2019-08_19 10:55
 */
public class KnapsackProblem {
    /* ��������������
     * weight��ÿ����Ʒ������
     * value��ÿ����Ʒ�ļ�ֵ
     * maxWeight���������ܵ��������
     */
    public int solve(int[] weight, int[] value, int maxWeight) {
        if (weight.length != value.length) {
            throw new RuntimeException("�����weight�����value�����С��һ��");
        }
        int len = weight.length;

        //v������ʾ��ǰrow����Ʒ���ܹ�װ������Ϊcolumn�ı����е�����ֵ
        int[][] v = new int[len + 1][maxWeight + 1];
        //path������¼ʵ������ֵʱѡ�����Ʒ
        boolean[][] path = new boolean[len + 1][maxWeight + 1];

        //��һ������v�ĵ�һ�к͵�һ����Ϊ0
        for (int i = 0; i <= len; ++i) {
            v[i][0] = 0;
        }
        for (int i = 0; i <= maxWeight; ++i) {
            v[0][i] = 0;
        }

        //�ڶ�������ȫv
        //row��ʾ��row����Ʒ
        //column��ʾ�������ܳ��ܵ��������
        for (int row = 1; row <= len; ++row) {
            for (int column = 1; column <= maxWeight; ++column) {
                if (column < weight[row - 1]) {
                    v[row][column] = v[row - 1][column];
                } else {
                    //v[row][column] = Math.max(v[row - 1][column], value[row-1] + v[row - 1][column - weight[row-1]]);
                    if (v[row - 1][column] < value[row - 1] + v[row - 1][column - weight[row - 1]]) {
                        v[row][column] = value[row - 1] + v[row - 1][column - weight[row - 1]];
                        //��path��Ӧ��λ���������
                        path[row][column] = true;
                    }
                }
            }
        }

        //�����������v���
        for (int[] arr : v) {
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("װ����ԣ�");

        //���Ĳ������ʵ������ֵʱѡ�����Ʒ
        int i = len;
        int j = maxWeight;
        while (i > 0 && j > 0) {
            if(path[i][j] == true){
                System.out.println("�ѵ�" + i +"����Ʒ������" + weight[i-1] + "�����뱳��");
                j -= weight[i-1];
            }
            --i;
        }
        return v[len][maxWeight];
    }

    @Test
    public void test() {
        int[] weight = {1, 4, 3};//��Ʒ������
        int[] value = {1500, 3000, 2000}; //��Ʒ�ļ�ֵ ����val[i] ����ǰ�潲��v[i]
        int maxWeight = 4; //����������

        solve(weight, value, maxWeight);
    }
}
