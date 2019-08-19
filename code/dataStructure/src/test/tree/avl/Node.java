package test.tree.avl;

/**
 * @author yejh
 * @create 2019-08_16 12:45
 */
public class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    //�����Ըý��Ϊ���������ĸ߶�
    public int height(){
        return Math.max(this.left == null? 0 : this.left.height(), this.right == null? 0 : this.right.height()) + 1;
    }

    //���ظý���������ĸ߶�
    public int leftHeight(){
        return this.left == null? 0 : this.left.height();
    }

    public int rightHeight(){
        return this.right == null? 0 : this.right.height();
    }

    public void leftRotate(){
        Node newNode = new Node(this.value);
        newNode.left = this.left;
        newNode.right = this.right.left;

        this.value = this.right.value;
        this.left = newNode;
        this.right = this.right.right;
    }

    public void rightRotate(){
        Node newNode = new Node(this.value);
        newNode.right = this.right;
        newNode.left = this.left.right;

        this.value = this.left.value;
        this.right = newNode;
        this.left = this.left.left;
    }

    public void add(Node node){
        if(node.value < this.value ){
            if(this.left ==null){
                this.left = new Node(node.value);
            }else{
                this.left.add(node);
            }
        }else{
            if(this.right == null){
                this.right = new Node(node.value);
            }else{
                this.right.add(node);
            }
        }

        this.adjust();
    }

    //����avl����ƽ��
    public void adjust(){
        //�������һ���������: (�������ĸ߶�-�������ĸ߶�) > 1 , ����ת
        if(rightHeight() - leftHeight() > 1) {
            //����������������������ĸ߶ȴ����������������������ĸ߶�
            if(right != null && right.leftHeight() > right.rightHeight()) {
                //�ȶ����ӽ���������ת
                right.rightRotate();
                //Ȼ���ڶԵ�ǰ����������ת
                leftRotate(); //����ת..
            } else {
                //ֱ�ӽ�������ת����
                leftRotate();
            }
            return ; //����Ҫ!!!
        }

        //�������һ��������� (�������ĸ߶� - �������ĸ߶�) > 1, ����ת
        if(leftHeight() - rightHeight() > 1) {
            //����������������������߶ȴ��������������ĸ߶�
            if(left != null && left.rightHeight() > left.leftHeight()) {
                //�ȶԵ�ǰ��������(������)->����ת
                left.leftRotate();
                //�ٶԵ�ǰ����������ת
                rightRotate();
            } else {
                //ֱ�ӽ�������ת����
                rightRotate();
            }
        }
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
        return "Node{" +
                "value=" + value +
                '}';
    }

    public void infixOrder(){
        if(this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right != null){
            this.right.infixOrder();
        }
    }

    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }

    public void postOrder(){
        if(this.left != null){
            this.left.postOrder();
        }
        if(this.right != null){
            this.right.postOrder();
        }
        System.out.println(this);
    }
}
