package test.tree.binarysorttree;

import org.junit.Test;

/**
 * @author yejh
 * @create 2019-08_15 21:59
 */


//��������������
public class BinarySortTree {

    private Node root;


    @Test
    public void test() {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        //ѭ������ӽ�㵽����������
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        //�����������������
        System.out.println("�����������������~");
        binarySortTree.infixOrder(); // 1, 3, 5, 7, 9, 10, 12

        //����һ��ɾ��Ҷ�ӽ��
        binarySortTree.delNode(12);


        binarySortTree.delNode(5);
        binarySortTree.delNode(10);
        binarySortTree.delNode(2);
        binarySortTree.delNode(3);

        binarySortTree.delNode(9);
        binarySortTree.delNode(1);
        binarySortTree.delNode(7);


        System.out.println("root=" + binarySortTree.getRoot());


        System.out.println("ɾ������");
        binarySortTree.infixOrder();

    }

    public Node getRoot() {
        return root;
    }

    //����Ҫɾ���Ľ��
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    //���Ҹ����
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    //��д����: 
    //1. ���ص� ��node Ϊ�����Ķ�������������С����ֵ
    //2. ɾ��node Ϊ�����Ķ�������������С���

    /**
     * @param node ����Ľ��(���������������ĸ����)
     * @return ���ص� ��node Ϊ�����Ķ�������������С����ֵ
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        //ѭ���Ĳ������ӽڵ㣬�ͻ��ҵ���Сֵ
        while (target.left != null) {
            target = target.left;
        }
        //��ʱ target��ָ������С���
        //ɾ����С���
        delNode(target.value);      //ע��˴�����������໥�ݹ飬��Ϊɾ����һ����Ҷ�ӽ��
        return target.value;
    }


    //ɾ�����
    /* ɾ���Ľ�����������£������
     * 1.ɾ�������
     *  1.1��������ӽڵ�
     *  1.2�������һ���ӽڵ�
     *  1.3������������ӽڵ�
     *
     * 2.ɾ��Ҷ�ӽ��
     *
     * 3.ɾ��֦�ڵ�
     *  3.1֦�����һ���ӽڵ�
     *  3.2֦����������ӽڵ�

     */
    public void delNode(int value) {
        if(root == null){
            return;
        }

        Node toDeleteNode = search(value);
        Node father = searchParent(value);
        if(toDeleteNode == null){
            return;
        }
       //1.�����
        if(toDeleteNode == root){
            if(toDeleteNode.left == null && toDeleteNode.right ==null){
                root = null;
            }else if(toDeleteNode.left != null){
                root = toDeleteNode.left;
            }else if(toDeleteNode.right != null){
                root = toDeleteNode.right;
            }else{
                //������������ӽڵ�
                int rightMinValue = delRightTreeMin(toDeleteNode.right);
                root.value = rightMinValue;
            }
        }
        else if(toDeleteNode.left == null && toDeleteNode.right ==null){
            //2.Ҷ�ӽ��
            if(father.left != null && father.left == toDeleteNode){
                father.left = null;
            }else {
                //��������һ�����ҽ��
                father.right = null;
            }
        }
        else{
            //3.1֦���
            if(toDeleteNode.left == null){
                //֦��������ӽ��
                if(father.left != null && father.left == toDeleteNode){
                    father.left = toDeleteNode.right;
                }else if(father.right != null && father.right == toDeleteNode){
                    father.right = toDeleteNode.right;
                }
            }else if(toDeleteNode.right == null){
                //֦��������ӽ��
                if(father.left != null && father.left == toDeleteNode){
                    father.left = toDeleteNode.left;
                }else if(father.right != null && father.right == toDeleteNode){
                    father.right = toDeleteNode.left;
                }
            }else{
                //֦����������ӽڵ�
                int rightMinValue = delRightTreeMin(toDeleteNode.right);
                toDeleteNode.value = rightMinValue;
            }
        }
    }

    //��ӽ��ķ���
    public void add(Node node) {
        if (root == null) {
            root = node;//���rootΪ����ֱ����rootָ��node
        } else {
            root.add(node);
        }
    }

    //�������
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("����������Ϊ�գ����ܱ���");
        }
    }
}

