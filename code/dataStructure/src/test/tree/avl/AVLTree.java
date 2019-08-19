package test.tree.avl;

import org.junit.Test;

/**
 * @author yejh
 * @create 2019-08_16 12:44
 *
 * 参考资料
 * https://www.cnblogs.com/skywang12345/p/3577479.html
 */
public class AVLTree {
    private Node root;

    @Test
    public void test() {
        AVLTree avlTree = new AVLTree();
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};
        for (int i = 0; i < arr.length; ++i) {
            avlTree.add(arr[i]);
        }

        System.out.println(avlTree.height());
        System.out.println(avlTree.root.leftHeight());
        System.out.println(avlTree.root.rightHeight());
        System.out.println();
        /*avlTree.infixOrder();
        System.out.println();
        avlTree.preOrder();
        System.out.println();
        avlTree.postOrder();*/

        for (int i = 0; i < arr.length; ++i) {
            avlTree.delNode(arr[i]);
        }
        avlTree.delNode(9);
        System.out.println(avlTree.height());
        //System.out.println(avlTree.root.leftHeight());
        //System.out.println(avlTree.root.rightHeight());
        avlTree.infixOrder();
    }

    public void add(Node node) {
        if (root == null) {
            root = node;
            return;
        }
        this.root.add(node);
    }

    public void add(int val) {
        if (root == null) {
            root = new Node(val);
            return;
        }
        this.root.add(new Node(val));
    }

    public Node search(int value){
        if(root == null){
            return null;
        }

        return root.search(value);
    }

    /**
     * 返回的 以node 为根结点的二叉排序树的最小结点的值
     *
     * @param node 传入的结点(当做二叉排序树的根结点)
     * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
     */
    public Node minimun(Node node) {
        Node target = node;
        //循环的查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        //这时 target就指向了最小结点
        //删除最小结点
        return target;
    }

    public Node maximun(Node node) {
        Node target = node;
        while (target.right != null) {
            target = target.right;
        }
        return target;
    }


    //avl树的结点删除方法
    //原理与二叉排序树删除结点的原理类似
    //但是要注意保持二叉树的平衡，所以在此采用与BinarySortTree.delNode(int value)不一样的思路
    /* 比较node.value和value
     * （1）如果value < node.value
     *  向node的左子结点查找后删除，并且调用adjust()调整平衡
     * （2）如果value > node.value
     *  向node的右子节点查找后删除，并且调用adjust()调整平衡
     * （3）如果value == node.value
     *  表示当前结点即要被删除的结点
     *      3.1 如果此结点的左右子节点都非空
     *          见下面代码注释
     *      3.2 如果此结点的一个子节点非空或都是null
     *          3.2.1 如果此结点为root，则将root置为它的左子结点或右子结点
     *          3.2.2 如果此结点不是root，则将它的父节点置为它的左子结点或右子结点
     *
     *
     */
    //该方法代码极为巧妙，并且运用了递归
    private Node delNode(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = delNode(node.left, value);
            //从删除结点所在层到最高层 层层调整，确保avl始终保持平衡状态
            node.adjust();

        } else if (value > node.value) {
            node.right = delNode(node.right, value);
            node.adjust();
        } else {
            //node即需要被删除的结点
            if (node.left != null && node.right != null) {
                // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                // 则(01)找出tree的右子树中的最小节点
                //   (02)将该最小节点的值赋值给node。
                //   (03)删除该最小节点。
                // 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                // 采用这种方式的好处是：删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                if (node.leftHeight() <= node.rightHeight()) {
                    Node min = minimun(node.right);
                    node.value = min.value;
                    //如果node.right是需要删除的结点，则会发生node.right = null，否则发生node.right = node.right
                    node.right = delNode(node.right, min.value);
                }
                // 反之同理
                else {
                    Node max = maximun(node.left);
                    node.value = max.value;
                    //如果node.right是需要删除的结点，则会发生node.left = null，否则发生node.left = node.left
                    node.left = delNode(node.left, max.value);
                }
            } else {
                if(node == root){
                    root = (node.left != null ? node.left : node.right);
                }else{
                    return node.left != null ? node.left : node.right;
                }

            }
        }
        return node;
    }

    public boolean delNode(int value) {
        if (search(value) == null) {
            return false;
        }
        delNode(root, value);
        return true;
    }

    public void infixOrder() {
        if (root == null) {
            System.out.println("avl树为空");
        } else {
            root.infixOrder();
        }
    }

    public void preOrder() {
        if (root == null) {
            System.out.println("avl树为空");
        } else {
            root.preOrder();
        }
    }

    public void postOrder() {
        if (root == null) {
            System.out.println("avl树为空");
        } else {
            root.postOrder();
        }
    }

    public int height() {
        return root == null ? 0 : root.height();
    }

}
