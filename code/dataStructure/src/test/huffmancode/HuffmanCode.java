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
    /* �շ�������ѹ������
     * msg����ѹ����Դ
     * ����ѹ������ֽ�����
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
        System.out.println("ѹ����ʱ��" + (zipEnd - zipStart));
        System.out.println("��ѹ��ʱ��" + (unZipEnd - zipEnd));
    }
    //��дһ����������ɶ�ѹ���ļ��Ľ�ѹ
    /**
     *
     * @param zipFile ׼����ѹ���ļ�
     * @param dstFile ���ļ���ѹ���ĸ�·��
     */
    public static void unZipFile(String zipFile, String dstFile) {

        //�����ļ�������
        InputStream is = null;
        //����һ������������
        ObjectInputStream ois = null;
        //�����ļ��������
        OutputStream os = null;
        try {
            //�����ļ�������
            is = new FileInputStream(zipFile);
            //����һ����  is�����Ķ���������
            ois = new ObjectInputStream(is);
            //��ȡbyte����  huffmanBytes
            byte[] huffmanBytes = (byte[])ois.readObject();
            //��ȡ�շ��������
            Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();

            //����
            byte[] bytes = decode(huffmanBytes, huffmanCodes);
            //��bytes ����д�뵽Ŀ���ļ�
            os = new FileOutputStream(dstFile);
            //д���ݵ� dstFile �ļ�
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

    //��д��������һ���ļ�����ѹ��
    /**
     *
     * @param srcFile �㴫���ϣ��ѹ�����ļ���ȫ·��
     * @param dstFile ����ѹ����ѹ���ļ��ŵ��ĸ�Ŀ¼
     */
    public static void zipFile(String srcFile, String dstFile) {

        //���������
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //�����ļ���������
        FileInputStream is = null;
        try {
            //�����ļ���������
            is = new FileInputStream(srcFile);
            //����һ����Դ�ļ���Сһ����byte[]
            byte[] b = new byte[is.available()];
            //��ȡ�ļ�
            is.read(b);
            //ֱ�Ӷ�Դ�ļ�ѹ��
            byte[] huffmanBytes = huffmanZip(b);
            //�����ļ��������, ���ѹ���ļ�
            os = new FileOutputStream(dstFile);
            //����һ�����ļ������������ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //�� �շ����������ֽ�����д��ѹ���ļ�
            oos.writeObject(huffmanBytes); //�����ǰ�
            //���������Զ������ķ�ʽд�� �շ������룬��Ϊ���Ժ����ǻָ�Դ�ļ�ʱʹ��
            //ע��һ��Ҫ�Ѻշ������� д��ѹ���ļ�
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

    /* ��ѹ����
     * �������̣�
     * huffmanBytes��[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
     * ��-88Ϊ������-88�Բ�����ʽת���ɶ����ƴ���-88 ==> 10101000
     * ʵ�����й�����
     * ��1������һ��Integer.toBinaryString(int) ����32λbit
     * -88 ==> 11111111111111111111111110101000 ����Ҫ��ȡ���λ
     * ��2�����������������ʱ�������Զ���ȡ��λ����������Ҫ�ֶ�����λ
     * 77 ==> 1001101
     * ��β���λ��������
     * ��3�������������������һ���ֽ�ʱ�������������ܲ���8λ��Ҫ���⿼��
     */
    public static byte[] decode(byte[] huffmanBytes, Map<Byte, String> codeSet) {
        //1.��huffmanBytesת����һ��������
        StringBuilder bitStr = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; ++i) {
            boolean isEnd = (i == huffmanBytes.length - 1);
            String each = byteTobitString(huffmanBytes[i], isEnd);
            //System.out.println(each);
            bitStr.append(each);
        }

        //2.Ϊ���빤����׼��
        //��ΪcodeSet�Ķ�Ӧ��ϵ��Byte --> String
        //�������ҪString --> Byte
        //������Ҫ����Map<String,Byte> decodeSet
        Map<String, Byte> decodeSet = new HashMap<>(codeSet.size());
        for (Map.Entry<Byte, String> entry : codeSet.entrySet()) {
            decodeSet.put(entry.getValue(), entry.getKey());
        }

        //3.�������ƴ�����
        List<Byte> byteList = new ArrayList<>();
        int count = 0;      //count������¼��ǰ�ֽڱ����λ��
        int i = 0;          //i��������bitStr
        while (i < bitStr.length()) {
            count++;
            String each = bitStr.substring(i, i + count);
            //ÿ�ζ����һ��each�Ƿ���Ͻ��뼯���κ�һ��
            Byte b = decodeSet.get(each);
            if (b == null) {
                //�����Ͻ��뼯

            } else {
                //���Ͻ��뼯��ĳһ��
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

        //��β�Ķ����ƴ�����Ҫ����λ
        //�ǽ�β�Ķ����ƴ����뿼�ǲ���λ
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

        //1.����Node�б����ַ���װ��Node��
        List<Node> nodes = getNodes(msg);

        //2.�����շ�����
        Node root = createHuffmanTree(nodes);

        //3.�����շ������뼯
        createCodeSet(root);

        //4.ѹ��msg�õ��ֽ�����
        byte[] res = zip(msg, codeSet);

        return res;
    }

    private static List<Node> getNodes(byte[] bytes) {
        //mapͳ��ÿһ��byte���ֵĴ���
        Map<Byte, Integer> map = new HashMap<>();

        for (int i = 0; i < bytes.length; ++i) {
            Integer count = map.get(bytes[i]);

            if (count == null) {
                map.put(bytes[i], 1);
            } else {
                map.put(bytes[i], count + 1);
            }

        }

        //��map�е�Byte-Integer������ȡ������װ��Node������nodeList������
        List<Node> nodeList = new ArrayList<>(map.size());
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            nodeList.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodeList;
    }

    private static Node createHuffmanTree(List<Node> nodeList) {


        while (nodeList.size() > 1) {
            //1.��Node����������
            Collections.sort(nodeList);

            //2.ȡ��Ȩֵ��С������Node
            Node node1 = nodeList.get(0);
            Node node2 = nodeList.get(1);

            //3.���츸�ڵ㣬�����γɺշ�����
            Node father = new Node(null, node1.getWeight() + node2.getWeight());
            father.setLeft(node1);
            father.setRight(node2);
            nodeList.add(father);

            //4.��Ҫ����ɾ�������ӽ��
            nodeList.remove(node1);
            nodeList.remove(node2);

        }
        //���ֻʣ��һ������㣬����
        return nodeList.get(0);
    }

    /* ���ݺշ����������շ������뼯���ص㷽����һ��
     *
     */
    private static void createCodeSet(Node root) {
        if (root != null) {
            StringBuilder stringBuilder = new StringBuilder();
            //��������ӽ�㣬�����ֽڶ�Ӧ�ı�������0
            createCodeSet(root.getLeft(), "0", stringBuilder);
            //��������ӽ�㣬�����ֽڶ�Ӧ�ı�������1
            createCodeSet(root.getRight(), "1", stringBuilder);
        }
    }

    private static void createCodeSet(Node node, String bit, StringBuilder stringBuilder) {
        StringBuilder newStringBuilder = new StringBuilder(stringBuilder);
        //ΪʲôҪ�������append���ú�����
        newStringBuilder.append(bit);

        //���node����Ҷ�ӽ�㣬����Ҫ���ӽ��ݹ�
        if (node.getData() == null) {
            if (node.getLeft() != null) {
                createCodeSet(node.getLeft(), "0", newStringBuilder);
            }
            if (node.getRight() != null) {
                createCodeSet(node.getRight(), "1", newStringBuilder);

            }
        } else {
            //node��Ҷ�ӽ��
            codeSet.put(node.getData(), newStringBuilder.toString());
        }
    }

    /* ����ѹ���ֽ����鰴�ս����ĺշ����ַ�������ѹ�����ص㷽�������
     *
     *
     */
    private static byte[] zip(byte[] msg, Map<Byte, String> codeSet) {
        //1.��msg��ÿ��Ԫ�ر��룬��������Ԫ�صı��뱣����һ��StringBuilder��
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < msg.length; ++i) {
            String code = codeSet.get(msg[i]);
            stringBuilder.append(code);
        }
        //System.out.println(stringBuilder);

        //2.��һ���������ַ�ת����byte[] res
        //2.1 ����res�ĳ��ȣ�ÿ�˸�bitΪһ�ֽڣ������8bit��Ҳ��1�ֽ�
        int len = (stringBuilder.length() + 7) / 8;
        byte[] res = new byte[len];

        //2.2 ��ÿ�˸�bit��String���ͣ�ת����byte
        for (int i = 0; i < len; ++i) {
            String byteStr;
            if (i != len - 1) {
                byteStr = stringBuilder.substring(8 * i, 8 * i + 8);
            } else {
                //�����ֽ�Ҫ����Դ�����Ϊ���ܲ���8bit
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