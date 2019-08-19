package com.atguigu.stack;

import java.util.Scanner;

/**
 * @author yejh
 * @create 2019-08_09 13:53
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        //测试一下ArrayStack 是否正确
        //先创建一个ArrayStack对象->表示栈
        LinkedListStack stack = new LinkedListStack(4);
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");

    }
}

class LinkedListStack {
    //内部类Node开始
    private class Node {
        private int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    '}';
        }
    }
    //内部类Node结束

    private int maxSize;
    private Node head;
    private Node top;
    private int count;

    public LinkedListStack(int maxSize) {
        this.maxSize = maxSize;
        head = new Node(0);
        top = head;
    }

    public boolean isFull() {
        return count == maxSize;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void push(int val) {
        if (isFull()) {
            System.out.println("栈已经满了，无法添加新数据！");
            return;
        }
        ++count;
        Node node = new Node(val);
        top.setNext(node);
        top = node;
    }

    public int pop() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("栈是空的，无法取出数据！");
        }
        --count;
        Node temp = head;
        while (temp.next != top) {
            temp = temp.next;
        }
        top = temp;
        return temp.next.val;
    }

    public void list(){

        if(isEmpty()){
            System.out.println("栈是空的！");
        }else{
            Node temp = head.next;
            listHelper(temp,1);
        }
    }

    private void listHelper(Node node, int n){
        if(n < count){
            listHelper(node.next, ++n);
        }else{
            System.out.println(node.val);
            return;
        }
        System.out.println(node.val);
    }
}
