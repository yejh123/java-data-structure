package test.tree.avl;

import org.junit.Test;

/**
 * @author yejh
 * @create 2019-08_16 12:44
 *
 * �ο�����
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
     * ���ص� ��node Ϊ�����Ķ�������������С����ֵ
     *
     * @param node ����Ľ��(���������������ĸ����)
     * @return ���ص� ��node Ϊ�����Ķ�������������С����ֵ
     */
    public Node minimun(Node node) {
        Node target = node;
        //ѭ���Ĳ������ӽڵ㣬�ͻ��ҵ���Сֵ
        while (target.left != null) {
            target = target.left;
        }
        //��ʱ target��ָ������С���
        //ɾ����С���
        return target;
    }

    public Node maximun(Node node) {
        Node target = node;
        while (target.right != null) {
            target = target.right;
        }
        return target;
    }


    //avl���Ľ��ɾ������
    //ԭ�������������ɾ������ԭ������
    //����Ҫע�Ᵽ�ֶ�������ƽ�⣬�����ڴ˲�����BinarySortTree.delNode(int value)��һ����˼·
    /* �Ƚ�node.value��value
     * ��1�����value < node.value
     *  ��node�����ӽ����Һ�ɾ�������ҵ���adjust()����ƽ��
     * ��2�����value > node.value
     *  ��node�����ӽڵ���Һ�ɾ�������ҵ���adjust()����ƽ��
     * ��3�����value == node.value
     *  ��ʾ��ǰ��㼴Ҫ��ɾ���Ľ��
     *      3.1 ����˽��������ӽڵ㶼�ǿ�
     *          ���������ע��
     *      3.2 ����˽���һ���ӽڵ�ǿջ���null
     *          3.2.1 ����˽��Ϊroot����root��Ϊ�������ӽ������ӽ��
     *          3.2.2 ����˽�㲻��root�������ĸ��ڵ���Ϊ�������ӽ������ӽ��
     *
     *
     */
    //�÷������뼫Ϊ������������˵ݹ�
    private Node delNode(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = delNode(node.left, value);
            //��ɾ��������ڲ㵽��߲� ��������ȷ��avlʼ�ձ���ƽ��״̬
            node.adjust();

        } else if (value > node.value) {
            node.right = delNode(node.right, value);
            node.adjust();
        } else {
            //node����Ҫ��ɾ���Ľ��
            if (node.left != null && node.right != null) {
                // ���tree��������������������(��������ȣ�������������������1)
                // ��(01)�ҳ�tree���������е���С�ڵ�
                //   (02)������С�ڵ��ֵ��ֵ��node��
                //   (03)ɾ������С�ڵ㡣
                // ����������"tree������������С�ڵ�"��"tree"������
                // �������ַ�ʽ�ĺô��ǣ�ɾ��"tree������������С�ڵ�"֮��AVL����Ȼ��ƽ��ġ�
                if (node.leftHeight() <= node.rightHeight()) {
                    Node min = minimun(node.right);
                    node.value = min.value;
                    //���node.right����Ҫɾ���Ľ�㣬��ᷢ��node.right = null��������node.right = node.right
                    node.right = delNode(node.right, min.value);
                }
                // ��֮ͬ��
                else {
                    Node max = maximun(node.left);
                    node.value = max.value;
                    //���node.right����Ҫɾ���Ľ�㣬��ᷢ��node.left = null��������node.left = node.left
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
            System.out.println("avl��Ϊ��");
        } else {
            root.infixOrder();
        }
    }

    public void preOrder() {
        if (root == null) {
            System.out.println("avl��Ϊ��");
        } else {
            root.preOrder();
        }
    }

    public void postOrder() {
        if (root == null) {
            System.out.println("avl��Ϊ��");
        } else {
            root.postOrder();
        }
    }

    public int height() {
        return root == null ? 0 : root.height();
    }

}
