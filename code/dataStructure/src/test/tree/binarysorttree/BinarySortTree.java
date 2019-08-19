package test.tree.binarysorttree;

import org.junit.Test;

/**
 * @author yejh
 * @create 2019-08_15 21:59
 */


//创建二叉排序树
public class BinarySortTree {

    private Node root;


    @Test
    public void test() {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加结点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树~");
        binarySortTree.infixOrder(); // 1, 3, 5, 7, 9, 10, 12

        //测试一下删除叶子结点
        binarySortTree.delNode(12);


        binarySortTree.delNode(5);
        binarySortTree.delNode(10);
        binarySortTree.delNode(2);
        binarySortTree.delNode(3);

        binarySortTree.delNode(9);
        binarySortTree.delNode(1);
        binarySortTree.delNode(7);


        System.out.println("root=" + binarySortTree.getRoot());


        System.out.println("删除结点后");
        binarySortTree.infixOrder();

    }

    public Node getRoot() {
        return root;
    }

    //查找要删除的结点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    //查找父结点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    //编写方法: 
    //1. 返回的 以node 为根结点的二叉排序树的最小结点的值
    //2. 删除node 为根结点的二叉排序树的最小结点

    /**
     * @param node 传入的结点(当做二叉排序树的根结点)
     * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        //循环的查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        //这时 target就指向了最小结点
        //删除最小结点
        delNode(target.value);      //注意此处并不会产生相互递归，因为删除的一定是叶子结点
        return target.value;
    }


    //删除结点
    /* 删除的结点点可能有如下？种情况
     * 1.删除根结点
     *  1.1根结点无子节点
     *  1.2根结点有一个子节点
     *  1.3根结点有两个子节点
     *
     * 2.删除叶子结点
     *
     * 3.删除枝节点
     *  3.1枝结点有一个子节点
     *  3.2枝结点有两个子节点

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
       //1.根结点
        if(toDeleteNode == root){
            if(toDeleteNode.left == null && toDeleteNode.right ==null){
                root = null;
            }else if(toDeleteNode.left != null){
                root = toDeleteNode.left;
            }else if(toDeleteNode.right != null){
                root = toDeleteNode.right;
            }else{
                //根结点有两个子节点
                int rightMinValue = delRightTreeMin(toDeleteNode.right);
                root.value = rightMinValue;
            }
        }
        else if(toDeleteNode.left == null && toDeleteNode.right ==null){
            //2.叶子结点
            if(father.left != null && father.left == toDeleteNode){
                father.left = null;
            }else {
                //非左结点则一定是右结点
                father.right = null;
            }
        }
        else{
            //3.1枝结点
            if(toDeleteNode.left == null){
                //枝结点有右子结点
                if(father.left != null && father.left == toDeleteNode){
                    father.left = toDeleteNode.right;
                }else if(father.right != null && father.right == toDeleteNode){
                    father.right = toDeleteNode.right;
                }
            }else if(toDeleteNode.right == null){
                //枝结点有左子结点
                if(father.left != null && father.left == toDeleteNode){
                    father.left = toDeleteNode.left;
                }else if(father.right != null && father.right == toDeleteNode){
                    father.right = toDeleteNode.left;
                }
            }else{
                //枝结点有两个子节点
                int rightMinValue = delRightTreeMin(toDeleteNode.right);
                toDeleteNode.value = rightMinValue;
            }
        }
    }

    //添加结点的方法
    public void add(Node node) {
        if (root == null) {
            root = node;//如果root为空则直接让root指向node
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，不能遍历");
        }
    }
}

