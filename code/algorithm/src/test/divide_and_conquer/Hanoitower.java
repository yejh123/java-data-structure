package test.divide_and_conquer;

import org.junit.Test;

/**
 * @author yejh
 * @create 2019-08_19 10:18
 */
public class Hanoitower {
    public static int count;
    public void hanoitower(int num, char a, char b, char c){
        if(num < 1){
            return;
        }
        if(num == 1){
            System.out.println(a + "->" + c);
            count++;
            return;
        }
        hanoitower(num-1,a,c,b);
        hanoitower(1,a,b,c);
        hanoitower(num-1,b,a,c);

    }

    @Test
    public void test(){
        hanoitower(5,'a','b','c');
        System.out.println(count);
    }
}
