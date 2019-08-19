package test.tree.binarysorttree;

/**
 * @author yejh
 * @create 2019-08_15 22:04
 */
public class Node {
    //����Node���
    int value;
    Node left;
    Node right;

    public Node(int value) {

        this.value = value;
    }


    //����Ҫɾ���Ľ��

    /**
     * @param value ϣ��ɾ���Ľ���ֵ
     * @return ����ҵ����ظý�㣬���򷵻�null
     */
    public Node search(int value) {
        if (value == this.value) { //�ҵ����Ǹý��
            return this;
        } else if (value < this.value) {//������ҵ�ֵС�ڵ�ǰ��㣬���������ݹ����
            //������ӽ��Ϊ��
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else { //������ҵ�ֵ��С�ڵ�ǰ��㣬���������ݹ����
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }

    }
    //����Ҫɾ�����ĸ����

    /**
     * @param value Ҫ�ҵ��Ľ���ֵ
     * @return ���ص���Ҫɾ���Ľ��ĸ���㣬���û�оͷ���null
     */
    public Node searchParent(int value) {
        //�����ǰ������Ҫɾ���Ľ��ĸ���㣬�ͷ���
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)) {
            return this;
        } else {
            //������ҵ�ֵС�ڵ�ǰ����ֵ, ���ҵ�ǰ�������ӽ�㲻Ϊ��
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value); //���������ݹ����
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value); //���������ݹ����
            } else {
                return null; // û���ҵ������
            }
        }

    }

    @Override
    public String toString() {
        return "Node [value=" + value + "]";
    }


    //��ӽ��ķ���
    //�ݹ����ʽ��ӽ�㣬ע����Ҫ���������������Ҫ��
    public void add(Node node) {
        if (node == null) {
            return;
        }

        //�жϴ���Ľ���ֵ���͵�ǰ�����ĸ�����ֵ��ϵ
        if (node.value < this.value) {
            //�����ǰ������ӽ��Ϊnull
            if (this.left == null) {
                this.left = node;
            } else {
                //�ݹ�������������
                this.left.add(node);
            }
        } else { //��ӵĽ���ֵ���� ��ǰ����ֵ
            if (this.right == null) {
                this.right = node;
            } else {
                //�ݹ�������������
                this.right.add(node);
            }

        }
    }

    //�������
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }


}
