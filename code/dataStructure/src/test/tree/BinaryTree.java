package test.tree;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author yejh
 * @create 2019-08_12 21:24
 */

/*
 *
 *
 *
 *
 */
public class BinaryTree {
    private Node root;
    private int count;

    //为了实现线索化，需要创建指向当前结点的前驱结点的指针
    //在递归进行线索化时，pre 总是保留前一个结点
    private Node pre = null;

    @Test
    public void toArrayTest() {
        BinaryTree bt = new BinaryTree();
        bt.add("dafi");
        bt.add("aofhina");
        bt.add("chiugas");
        bt.add("rehiufda");
        String[] objs = bt.toArray();
        System.out.println(Arrays.toString(objs));
    }

    @Test
    public void threadedNodesTest() {
        BinaryTree bt = new BinaryTree();
        Node node1 = new Node("dafi");
        Node node2 = new Node("aofhina");
        Node node3 = new Node("chiugas");
        Node node4 = new Node("rehiufda");
        Node node5 = new Node("faooadj");
        Node node6 = new Node("dhuiqpicxno");
        Node node7 = new Node("xoakdo");

        bt.add(node1);
        bt.add(node2);
        bt.add(node3);
        bt.add(node4);
        bt.add(node5);
        bt.add(node6);
        bt.add(node7);

        String[] objs = bt.toArray();
        System.out.println(Arrays.toString(objs));

        bt.threadedNodes();
        System.out.println(Arrays.toString(bt.toArray()));

        System.out.println(node3 + "的前驱节点和后驱节点分别为：" + node3.getLeft() + " " + node3.getRight());
        System.out.println(node5 + "的前驱节点和后驱节点分别为：" + node5.getLeft() + " " + node5.getRight());

        String[] arr = bt.threadedList();
        System.out.println(Arrays.toString(arr));

    }


    //添加子节点
    public void add(String com) {
        if (this.root == null) {
            this.root = new Node(com);
        } else {
            Node node = new Node(com);
            this.root.addNode(node);
        }

        ++this.count;
    }

    public void add(Node node) {
        if (this.root == null) {
            this.root = node;
        } else {
            this.root.addNode(node);
        }

        ++this.count;
    }

    //中序遍历得到数组
    public String[] toArray() {
        if (this.root == null) {
            return null;
        } else {
            String[] arr = new String[count];
            root.toArrayNode(arr);
            return arr;
        }
    }


    /*对二叉树进行中序线索化
     */
    public void threadedNodes() {
        //调用重载方法
        threadedNodes(root);
    }

    /**
     * 对二叉树进行中序线索化
     *
     * @param node 就是当前需要线索化的结点
     */
    private void threadedNodes(Node node) {
        if (node == null) {
            return;
        }

        //(一)先线索化左子树
        threadedNodes(node.getLeft());

        //(二)线索化当前结点[有难度]

        //处理当前结点的前驱结点
        //以8结点来理解
        //8结点的.left = null , 8结点的.leftType = 1
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }

        //处理后继结点
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }

        //!!! 每处理一个结点后，让当前结点是下一个结点的前驱结点
        pre = node;

        //(三)在线索化右子树
        threadedNodes(node.getRight());
    }

    //使用线索化遍历方式打印树节点
    public String[] threadedList() {
        String[] arr = new String[count];
        int foot = 0;
        Node node = this.root;
        while (node != null) {
            //先找到leftTpye为1的节点
            while (node.getLeftType() != 1) {
                node = node.getLeft();
            }

            arr[foot++] = node.getData();

            //遍历尽可能多的后续节点
            while (node.getRightType() == 1) {
                node = node.getRight();
                arr[foot++] = node.getData();
            }

            node = node.getRight();
        }
        return arr;
    }
}

class Node {
    private String data;
    private Node left;
    private Node right;
    private static int foot;

    //线索化二叉树
    private int leftType;
    private int rightType;

    public Node(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public void addNode(Node newNode) {
        if (this.data.compareTo(newNode.data) > 0) {
            if (this.left == null) {
                this.left = newNode;
            } else {
                this.left.addNode(newNode);
            }
        } else if (this.data.compareTo(newNode.data) < 0) {
            if (this.right == null) {
                this.right = newNode;
            } else {
                this.right.addNode(newNode);
            }
        } else {
            System.out.println("不能添加重复的节点");
        }

    }


    public void toArrayNode(String[] arr) {
        foot = 0;
        toArrayNodeHelper(arr);
    }

    private void toArrayNodeHelper(String[] arr) {

        if (this.left != null && this.getLeftType() == 0) {
            this.left.toArrayNodeHelper(arr);
        }

        arr[foot++] = this.data;

        if (this.right != null && this.getRightType() == 0) {
            this.right.toArrayNodeHelper(arr);
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "data='" + data + '\'' +
                '}';
    }
}