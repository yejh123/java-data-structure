package test.reversepolishcal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author yejh
 * @create 2019-08_10 10:55
 */
public class ReversePoland {
    //���ܻ���ֵķ���*/
    private static final String ADD = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";
    private static final String LEFT = "(";
    private static final String RIGHT = ")";


    /*������������ȼ�*/
    private static final int LEVEL_1 = 1;   //+ -
    private static final int LEVEL_2 = 2;   //* /
    private static final int LEVEL_MAX = Integer.MAX_VALUE;   //( )

    public static double calcWithExpression(String expression){
        List<String> infix = toInfixList(expression);
        List<String> suffix = toSuffixExpression(infix);
        return calcWithSuffix(suffix);
    }

    private static boolean isNumber(String str) {
        return str.matches("^[+-]?[\\.\\d]+$");
    }

    private static boolean isSymbol(String str) {
        return str.matches("\\+|-|\\*|/|\\(|\\)");
    }

    private static int getPriority(String oper) {
        switch (oper) {
            case ADD:
                return LEVEL_1;

            case MINUS:
                return LEVEL_1;

            case MULTIPLY:
                return LEVEL_2;

            case DIVIDE:
                return LEVEL_2;

            case LEFT:
                return LEVEL_MAX;

            case RIGHT:
                return LEVEL_MAX;

            default:
                throw new RuntimeException("û��ƥ��������");
        }

    }

    @Test
    public void test1() {
        System.out.println(isNumber("5"));
        System.out.println(isNumber("52"));

        System.out.println(isNumber("-52"));
        System.out.println(isNumber("5.2"));
        System.out.println(isNumber("52.52"));
        System.out.println(isNumber("-52.52"));
        System.out.println(isNumber("+52.52"));


        System.out.println(isNumber("+0.1"));
        System.out.println(isNumber("-00.55"));

    }

    /*
    ����׺���ʽת��Ϊ��׺list
    s="1+((25+3)��4)-5";
     */
    private static List<String> toInfixList(String infixStr) {
        if(infixStr == null || infixStr.isEmpty()){
            return null;
        }
        int len = infixStr.length();
        List<String> infixList = new ArrayList<>(len);
        for(int i = 0; i < len; ++i){
            String each = "";
            char c = infixStr.charAt(i);
            if(c < 48 || c > 57){
                infixList.add("" + c);
            }
            else{
                while (i < len && (c == '.' || c >= 48 && c <= 57)) {
                    each += c;//ƴ��
                    i++;
                    if(i < len){
                        c = infixStr.charAt(i);
                    }
                }
                infixList.add(each);
                --i;
            }
        }
        return infixList;
    }

    @Test
    public void test2() {
        String string = "12.8+(2-3.55)*4+10/5.0";
        List<String> list1= toInfixList(string);
        System.out.println(list1);
        /*for(String str: list1){
            System.out.println(isNumber(str));
        }*/

        List<String> suffix = toSuffixExpression(list1);

        System.out.println(suffix);
        System.out.println(calcWithSuffix(suffix));
    }

    private static List<String> toSuffixExpression(List<String> infixList) {
        if(infixList == null || infixList.isEmpty()){
            return null;
        }
        int len = infixList.size();
        Stack<String> stack = new Stack<>();
        List<String> suffixList = new ArrayList<>(len);
        for(int i = 0; i < len; ++i){
            String each = infixList.get(i);
            if(isNumber(each)){
                suffixList.add(each);
            }
            else if(LEFT.equals(each)){
                stack.push(each);
            }
            else if(RIGHT.equals(each)){
                while(!LEFT.equals(stack.peek())){
                    suffixList.add(stack.pop());
                }
                //��"("����
                stack.pop();
            }
            //���������
            else {
                if(stack.isEmpty() || LEFT.equals(stack.peek()) ){
                    stack.push(each);
                }
                else{
                    while(!stack.isEmpty() && getPriority(each) <= getPriority(stack.peek())){
                        suffixList.add(stack.pop());
                    }
                    stack.push(each);
                }
            }
        }
        //��ջ��ʣ���Ԫ���ƶ���suffixList��
        while(!stack.isEmpty()){
            suffixList.add(stack.pop());
        }
        return suffixList;
    }

    /*
    ͨ����׺���ʽ������
    [2.6, +, (, (, 25.0, +, 3.8, ), *, 4, ), -, 5]
    [2.6, 25.0, 3.8, +, 4, *, +, 5, -]
     */
    private static double calcWithSuffix(List<String> suffixList){
        if(suffixList == null || suffixList.isEmpty()){
            throw new RuntimeException("calcWithSuffix():���벻�Ϸ���suffixList");
        }
        int len = suffixList.size();

        Stack<String> stack = new Stack<>();
        for(int i = 0; i < len; ++i){
            String each = suffixList.get(i);
            //���ƥ�䵽����
            if(isNumber(each)){
                stack.push(each);
            }
            //���ƥ�䵽�����
            else{
                double num2 = Double.parseDouble(stack.pop());
                double num1 = Double.parseDouble(stack.pop());
                double res = 0;
                if (each.equals("+")) {
                    res = num1 + num2;
                } else if (each.equals("-")) {
                    res = num1 - num2;
                } else if (each.equals("*")) {
                    res = num1 * num2;
                } else if (each.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("���������");
                }
                //��res ��ջ
                stack.push("" + res);
            }
        }
        return Double.parseDouble(stack.pop());
    }
}
