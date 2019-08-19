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

    //返回以该结点为根结点的树的高度
    public int height(){
        return Math.max(this.left == null? 0 : this.left.height(), this.right == null? 0 : this.right.height()) + 1;
    }

    //返回该结点左子树的高度
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

    //调整avl树的平衡
    public void adjust(){
        //当添加完一个结点后，如果: (右子树的高度-左子树的高度) > 1 , 左旋转
        if(rightHeight() - leftHeight() > 1) {
            //如果它的右子树的左子树的高度大于它的右子树的右子树的高度
            if(right != null && right.leftHeight() > right.rightHeight()) {
                //先对右子结点进行右旋转
                right.rightRotate();
                //然后在对当前结点进行左旋转
                leftRotate(); //左旋转..
            } else {
                //直接进行左旋转即可
                leftRotate();
            }
            return ; //必须要!!!
        }

        //当添加完一个结点后，如果 (左子树的高度 - 右子树的高度) > 1, 右旋转
        if(leftHeight() - rightHeight() > 1) {
            //如果它的左子树的右子树高度大于它的左子树的高度
            if(left != null && left.rightHeight() > left.leftHeight()) {
                //先对当前结点的左结点(左子树)->左旋转
                left.leftRotate();
                //再对当前结点进行右旋转
                rightRotate();
            } else {
                //直接进行右旋转即可
                rightRotate();
            }
        }
    }

    //查找要删除的结点

    /**
     * @param value 希望删除的结点的值
     * @return 如果找到返回该结点，否则返回null
     */
    public Node search(int value) {
        if (value == this.value) { //找到就是该结点
            return this;
        } else if (value < this.value) {//如果查找的值小于当前结点，向左子树递归查找
            //如果左子结点为空
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else { //如果查找的值不小于当前结点，向右子树递归查找
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }

    }
    //查找要删除结点的父结点

    /**
     * @param value 要找到的结点的值
     * @return 返回的是要删除的结点的父结点，如果没有就返回null
     */
    public Node searchParent(int value) {
        //如果当前结点就是要删除的结点的父结点，就返回
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)) {
            return this;
        } else {
            //如果查找的值小于当前结点的值, 并且当前结点的左子结点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value); //向左子树递归查找
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value); //向右子树递归查找
            } else {
                return null; // 没有找到父结点
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
