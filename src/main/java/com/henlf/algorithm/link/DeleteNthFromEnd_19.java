package com.henlf.algorithm.link;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * 示例：
 *
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 *
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 *
 * 给定的 n 保证是有效的。
 *
 * 进阶：
 *
 * 你能尝试使用一趟扫描实现吗？
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 */
public class DeleteNthFromEnd_19 {
    /**
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (null == head) {
            return null;
        }

        int length = length(head);

        // 移动步伐
        int step = length - n;

        // 被删节点
        ListNode deleted = head;
        // 被删节点的前节点
        ListNode pre = null;
        while (step > 0) {
            pre = deleted;
            deleted = deleted.next;
            --step;
        }

        if (deleted == head) {
            head = head.next;
        } else {
            pre.next = deleted.next;
        }

        return head;
    }

    /**
     * 链表长度
     * @param head
     * @return
     */
    private int length(final ListNode head) {
        int length = 0;

        ListNode current = head;
        while (null != current) {
            ++length;
            current = current.next;
        }

        return length;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
