package com.henlf.algorithm.link;

import java.util.Objects;
import java.util.Random;

/**
 * 跳跃列表
 * @author tanghongfeng
 * @date 2021-08-11 08:58
 */
public class SkipList {
    // 结点“晋升”的概率
    private static final double PROMOTE_RATE = 0.5;

    private Node head;
    private Node tail;

    private int maxLevel;

    public SkipList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);

        head.right = tail;
        tail.left = head;
    }

    /**
     * 查找
     * @param data 节点值
     * @return 节点
     */
    public Node search(int data) {
        Node node = findNode(data);
        if (node.data == data) {
            System.out.println("找到结点：" + data);
            return node;
        }

        System.out.println("未找到结点：" + data);
        return null;
    }

    /**
     * 查找包含指定值的节点
     * @param data
     * @return
     */
    private Node findNode(int data) {
        Node node = head;
        while (true) {
            while (node.right.data != Integer.MAX_VALUE && node.right.data <= data) {
                node = node.right;
            }

            if (Objects.isNull(node.down)) {
                break;
            }

            node = node.down;
        }

        return node;
    }

    /**
     * 插入节点
     * @param data
     */
    public void insert(int data) {
        Node preNode = findNode(data);
        // 如果 data 相同，直接返回
        if (preNode.data == data) {
            return;
        }

        Node node = new Node(data);
        int currentLevel = 0;

        appendNode(preNode, node);

        // 随机决定结点是否“晋升”
        Random random = new Random();
        while (random.nextDouble() < PROMOTE_RATE) {
            // 如果当前层已经是最高层，需要增加一层
            if (currentLevel == maxLevel) {
                addLevel();
            }

            // 找到上一层的前置节点
            while (preNode.up == null) {
                preNode = preNode.left;
            }

            preNode = preNode.up;

            // 把“晋升”的新结点插入到上一层
            Node upperNode = new Node(data);
            appendNode(preNode, upperNode);
            upperNode.down = node;
            node.up = upperNode;
            node = upperNode;
            currentLevel++;
        }
    }

    /**
     * 在前置结点后面添加新结点
     * @param preNode
     * @param newNode
     */
    private void appendNode(Node preNode, Node newNode){
        newNode.left = preNode;
        newNode.right = preNode.right;
        preNode.right.left = newNode;
        preNode.right = newNode;
    }

    /**
     * 增加一层
     */
    private void addLevel(){
        maxLevel++;
        Node p1 = new Node(Integer.MIN_VALUE);
        Node p2 = new Node(Integer.MAX_VALUE);
        p1.right = p2;
        p2.left = p1;
        p1.down = head;
        head.up = p1;
        p2.down = tail;
        tail.up = p2;
        head = p1;
        tail = p2;
    }

    /**
     * 删除结点
     * @param data
     * @return
     */
    public boolean remove(int data){
        Node removedNode = search(data);
        if(removedNode == null){
            return false;
        }

        int currentLevel = 0;
        while (removedNode != null){
            removedNode.right.left = removedNode.left;
            removedNode.left.right = removedNode.right;
            //如果不是最底层，且只有无穷小和无穷大结点，删除该层
            if(currentLevel != 0 && removedNode.left.data == Integer.MIN_VALUE && removedNode.right.data == Integer.MAX_VALUE){
                removeLevel(removedNode.left);
            }else {
                currentLevel++;
            }

            removedNode = removedNode.up;
        }

        return true;
    }

    /**
     * 删除一层
     * @param leftNode
     */
    private void removeLevel(Node leftNode){
        Node rightNode = leftNode.right;
        //如果删除层是最高层
        if (leftNode.up == null){
            leftNode.down.up = null;
            rightNode.down.up = null;
        } else {
            leftNode.up.down = leftNode.down;
            leftNode.down.up = leftNode.up;
            rightNode.up.down = rightNode.down;
            rightNode.down.up = rightNode.up;
        }

        maxLevel --;
    }

    /**
     * 输出底层链表
     */
    public void printList() {
        Node node = head;
        while (node.down != null) {
            node = node.down;
        }

        while (node.right.data != Integer.MAX_VALUE) {
            System.out.print(node.right.data + " ");
            node = node.right;
        }

        System.out.println();
    }

    public static class Node {
        private int data;
        private Node up;
        private Node down;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        SkipList list = new SkipList();
        list.insert(50);
        list.insert(15);
        list.insert(13);
        list.insert(20);
        list.insert(100);
        list.insert(75);
        list.insert(99);
        list.insert(76);
        list.insert(83);
        list.insert(65);
        list.printList();
        list.search(50);
        list.remove(50);
        list.search(50);
    }
}
