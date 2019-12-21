package com.henlf.link;

/**
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 *
 * 示例 1:
 *
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * 示例 2:
 *
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步: 0->1->2->NULL
 *
 * 链接：https://leetcode-cn.com/problems/rotate-list
 */
public class Rotate_61 {
    public static ListNode rotateRight(ListNode head, int k) {
        if (null == head || k < 0) {
            throw new IllegalArgumentException();
        }

        // 链表大小
        int size = size(head);
        int step = size > k ? (size - k) : k % size == 0 ? 0 : size - k % size;
        if (size == 1 || step == 0) {
            return head;
        }

        // 新链表尾部
        ListNode tail = head;
        for (int i = 1; i < step; i++) {
            tail = tail.next;
        }

        // 新链表头部
        ListNode newHead = tail.next;
        // 旧链表尾部
        ListNode current = newHead;
        tail.next = null;
        while (null != current && null != current.next) {
            current = current.next;
        }

        current.next = head;

        return newHead;
    }

    private static int size(final ListNode head) {
        int size = 0;
        ListNode current = head;
        while (null != current) {
            ++size;
            current = current.next;
        }

        return size;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
