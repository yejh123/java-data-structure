package test.huffmancode;

import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * @author yejh
 * @create 2019-08_14 21:24
 */
public class HuffmanCode {
    private static Map<Byte, String> codeSet = new HashMap<>();

    /*
    "i like like like java do you like a java"
    1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100
     */
    /* 赫夫曼编码压缩方法
     * msg：待压缩资源
     * 返回压缩后的字节数组
     *
     */
    @Test
    public void test() {
        String msgStr = "i like like like java do you like a java";
        byte[] msg = msgStr.getBytes();
        List<Node> nodes = getNodes(msg);
        System.out.println(nodes);

        Node root = createHuffmanTree(nodes);
        //preOrder(root);

        createCodeSet(root);
        System.out.println(codeSet);
        //{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}

        byte[] res = zip(msg, codeSet);
        System.out.println(Arrays.toString(res));
        //[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]

        byte[] msg2 = decode(res, codeSet);
        System.out.println(Arrays.toString(msg2));
        //[105, 32, 108, 105, 107, 101, 32, 108, 105, 107, 101, 32, 108, 105, 107, 101, 32, 106, 97, 118, 97, 32, 100, 111, 32, 121, 111, 117, 32, 108, 105, 107, 101, 32, 97, 32, 106, 97, 118, 97]

        System.out.println(new String(msg2));
        //i like like like java do you like a java
    }

    @Test
    public void fileTest(){
        long zipStart = System.currentTimeMillis();
        zipFile("C:\\file\\test.png","C:\\file\\zip");
        long zipEnd = System.currentTimeMillis();
        unZipFile("C:\\file\\zip","C:\\file\\test2.png");
        long unZipEnd = System.currentTimeMillis();
        System.out.println("压缩用时：" + (zipEnd - zipStart));
        System.out.println("解压用时：" + (unZipEnd - zipEnd));
    }
    //编写一个方法，完成对压缩文件的解压
    /**
     *
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {

        //定义文件输入流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义文件的输出流
        OutputStream os = null;
        try {
            //创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和  is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组  huffmanBytes
            byte[] huffmanBytes = (byte[])ois.readObject();
            //读取赫夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();

            //解码
            byte[] bytes = decode(huffmanBytes, huffmanCodes);
            //将bytes 数组写入到目标文件
            os = new FileOutputStream(dstFile);
            //写数据到 dstFile 文件
            os.write(bytes);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //编写方法，将一个文件进行压缩
    /**
     *
     * @param srcFile 你传入的希望压缩的文件的全路径
     * @param dstFile 我们压缩后将压缩文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {

        //创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //创建文件的输入流
        FileInputStream is = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的byte[]
            byte[] b = new byte[is.available()];
            //读取文件
            is.read(b);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            //创建文件的输出流, 存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把 赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes); //我们是把
            //这里我们以对象流的方式写入 赫夫曼编码，是为了以后我们恢复源文件时使用
            //注意一定要把赫夫曼编码 写入压缩文件
            oos.writeObject(codeSet);


        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /* 解压方法
     * 解析过程：
     * huffmanBytes：[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
     * 以-88为例，将-88以补码形式转化成二进制串：-88 ==> 10101000
     * 实际运行过程中
     * （1）问题一：Integer.toBinaryString(int) 返回32位bit
     * -88 ==> 11111111111111111111111110101000 所以要截取后八位
     * （2）问题二：当处理负数时，程序自动截取高位，所以我们要手动补高位
     * 77 ==> 1001101
     * 如何补高位？见代码
     * （3）问题三：当处理到最后一个字节时，二进制数可能不足8位，要特殊考虑
     */
    public static byte[] decode(byte[] huffmanBytes, Map<Byte, String> codeSet) {
        //1.将huffmanBytes转化成一串二进制
        StringBuilder bitStr = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; ++i) {
            boolean isEnd = (i == huffmanBytes.length - 1);
            String each = byteTobitString(huffmanBytes[i], isEnd);
            //System.out.println(each);
            bitStr.append(each);
        }

        //2.为解码工作做准备
        //因为codeSet的对应关系是Byte --> String
        //而如今需要String --> Byte
        //所以需要创建Map<String,Byte> decodeSet
        Map<String, Byte> decodeSet = new HashMap<>(codeSet.size());
        for (Map.Entry<Byte, String> entry : codeSet.entrySet()) {
            decodeSet.put(entry.getValue(), entry.getKey());
        }

        //3.将二进制串解码
        List<Byte> byteList = new ArrayList<>();
        int count = 0;      //count用作记录当前字节编码的位数
        int i = 0;          //i用作遍历bitStr
        while (i < bitStr.length()) {
            count++;
            String each = bitStr.substring(i, i + count);
            //每次都检查一下each是否符合解码集中任何一个
            Byte b = decodeSet.get(each);
            if (b == null) {
                //不符合解码集

            } else {
                //符合解码集中某一个
                i += count;
                count = 0;
                byteList.add(b);
            }
        }

        byte[] msg = new byte[byteList.size()];
        for (int t = 0; t < msg.length; ++t) {
            msg[t] = byteList.get(t);
        }
        return msg;
    }

    private static String byteTobitString(byte aByte, boolean isEnd) {
        int i = (int) aByte;

        //结尾的二进制串不需要补高位
        //非结尾的二进制串必须考虑补高位
        if (!isEnd) {
            i = i | 256;
        }
        String bitString;
        bitString = Integer.toBinaryString(i);
        if (!isEnd) {
            bitString = bitString.substring(bitString.length() - 8);
        }
        return bitString;
    }

    public static byte[] huffmanZip(byte[] msg) {

        //1.创建Node列表，将字符封装在Node中
        List<Node> nodes = getNodes(msg);

        //2.构建赫夫曼树
        Node root = createHuffmanTree(nodes);

        //3.建立赫夫曼编码集
        createCodeSet(root);

        //4.压缩msg得到字节数组
        byte[] res = zip(msg, codeSet);

        return res;
    }

    private static List<Node> getNodes(byte[] bytes) {
        //map统计每一个byte出现的次数
        Map<Byte, Integer> map = new HashMap<>();

        for (int i = 0; i < bytes.length; ++i) {
            Integer count = map.get(bytes[i]);

            if (count == null) {
                map.put(bytes[i], 1);
            } else {
                map.put(bytes[i], count + 1);
            }

        }

        //将map中的Byte-Integer对依次取出，封装成Node保存在nodeList容器中
        List<Node> nodeList = new ArrayList<>(map.size());
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            nodeList.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodeList;
    }

    private static Node createHuffmanTree(List<Node> nodeList) {


        while (nodeList.size() > 1) {
            //1.将Node以升序排列
            Collections.sort(nodeList);

            //2.取出权值最小的两个Node
            Node node1 = nodeList.get(0);
            Node node2 = nodeList.get(1);

            //3.构造父节点，连接形成赫夫曼树
            Node father = new Node(null, node1.getWeight() + node2.getWeight());
            father.setLeft(node1);
            father.setRight(node2);
            nodeList.add(father);

            //4.不要忘记删除两个子结点
            nodeList.remove(node1);
            nodeList.remove(node2);

        }
        //最后只剩下一个根结点，返回
        return nodeList.get(0);
    }

    /* 根据赫夫曼树建立赫夫曼编码集（重点方法其一）
     *
     */
    private static void createCodeSet(Node root) {
        if (root != null) {
            StringBuilder stringBuilder = new StringBuilder();
            //如果是左子结点，则在字节对应的编码后面加0
            createCodeSet(root.getLeft(), "0", stringBuilder);
            //如果是右子结点，则在字节对应的编码后面加1
            createCodeSet(root.getRight(), "1", stringBuilder);
        }
    }

    private static void createCodeSet(Node node, String bit, StringBuilder stringBuilder) {
        StringBuilder newStringBuilder = new StringBuilder(stringBuilder);
        //为什么要在这里才append？好好想想
        newStringBuilder.append(bit);

        //如果node不是叶子结点，则需要向子结点递归
        if (node.getData() == null) {
            if (node.getLeft() != null) {
                createCodeSet(node.getLeft(), "0", newStringBuilder);
            }
            if (node.getRight() != null) {
                createCodeSet(node.getRight(), "1", newStringBuilder);

            }
        } else {
            //node是叶子结点
            codeSet.put(node.getData(), newStringBuilder.toString());
        }
    }

    /* 将待压缩字节数组按刚建立的赫夫曼字符集进行压缩（重点方法其二）
     *
     *
     */
    private static byte[] zip(byte[] msg, Map<Byte, String> codeSet) {
        //1.将msg中每个元素编码，并将所有元素的编码保存在一个StringBuilder中
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < msg.length; ++i) {
            String code = codeSet.get(msg[i]);
            stringBuilder.append(code);
        }
        //System.out.println(stringBuilder);

        //2.将一串二进制字符转化成byte[] res
        //2.1 计算res的长度，每八个bit为一字节，最后不足8bit的也算1字节
        int len = (stringBuilder.length() + 7) / 8;
        byte[] res = new byte[len];

        //2.2 将每八个bit（String类型）转化成byte
        for (int i = 0; i < len; ++i) {
            String byteStr;
            if (i != len - 1) {
                byteStr = stringBuilder.substring(8 * i, 8 * i + 8);
            } else {
                //最后的字节要特殊对待，因为可能不足8bit
                byteStr = stringBuilder.substring(8 * i);
            }
            res[i] = (byte) Integer.parseInt(byteStr, 2);
        }
        return res;
    }

    private static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        }
    }
}

class Node implements Comparable<Node> {
    private Byte data;
    private int weight;
    private Node left;
    private Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    public Byte getData() {
        return data;
    }

    public void setData(Byte data) {
        this.data = data;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    public void preOrder() {
        System.out.println(this);

        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }
    }
}