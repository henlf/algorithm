package com.henlf.link;

/**
 * 定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 *
 * 你应当保留两个分区中每个节点的初始相对位置。
 *
 * 示例:
 *
 * 输入: head = 1->4->3->2->5->2, x = 3
 * 输出: 1->2->2->4->3->5
 *
 * 链接：https://leetcode-cn.com/problems/partition-list
 */
public class SplitLink_86 {

    private Node head;

    /**
     * 根据指定元素{@code x}分隔链表，并保持链表元素相对位置 <br />
     * <p>
     *     两个链表，最后链接
     * </p>
     * @param head 链表
     * @param x 分隔元素
     * @return 分隔后的链表
     */
    public SplitLink_86 partition(Node head, int x) {
        if (head == null) {
            return null;
        }

        SplitLink_86 partitioned = new SplitLink_86();

        Node leftHead = null;
        Node leftCurrent = null;
        Node rightHead = null;
        Node rightCurrent = null;
        Node current = head;

        while (null != current) {
            if (current.val < x) {
                if (null == leftHead) {
                    leftHead = leftCurrent = current;
                } else {
                    leftCurrent.next = current;
                    leftCurrent = leftCurrent.next;
                }

                current = current.next;
            } else {
                if (null == rightHead) {
                    rightHead = rightCurrent = current;
                } else {
                    rightCurrent.next = current;
                    rightCurrent = rightCurrent.next;
                }

                current = current.next;
            }
        }

        if (leftCurrent != null) {
            leftCurrent.next = rightHead;
            partitioned.head = leftHead;

            if (null != rightCurrent) {
                rightCurrent.next = null;
            }
        } else if (rightCurrent != null) {
            partitioned.head = rightHead;
        }

        return partitioned;
    }

    private static class Node {
        int val;
        Node next;
        Node(int x) { val = x; }
    }
}
