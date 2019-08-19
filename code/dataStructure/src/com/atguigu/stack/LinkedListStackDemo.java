package com.atguigu.stack;

import java.util.Scanner;

/**
 * @author yejh
 * @create 2019-08_09 13:53
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        //����һ��ArrayStack �Ƿ���ȷ
        //�ȴ���һ��ArrayStack����->��ʾջ
        LinkedListStack stack = new LinkedListStack(4);
        String key = "";
        boolean loop = true; //�����Ƿ��˳��˵�
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show: ��ʾ��ʾջ");
            System.out.println("exit: �˳�����");
            System.out.println("push: ��ʾ������ݵ�ջ(��ջ)");
            System.out.println("pop: ��ʾ��ջȡ������(��ջ)");
            System.out.println("���������ѡ��");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("������һ����");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("��ջ�������� %d\n", res);
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

        System.out.println("�����˳�~~~");

    }
}

class LinkedListStack {
    //�ڲ���Node��ʼ
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
    //�ڲ���Node����

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
            System.out.println("ջ�Ѿ����ˣ��޷���������ݣ�");
            return;
        }
        ++count;
        Node node = new Node(val);
        top.setNext(node);
        top = node;
    }

    public int pop() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("ջ�ǿյģ��޷�ȡ�����ݣ�");
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
            System.out.println("ջ�ǿյģ�");
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
