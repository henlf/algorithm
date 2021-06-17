package com.henlf.algorithm.link;

/**
 * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
 * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 * 示例 1:
 *
 * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
 * 示例 2:
 *
 * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
 *
 * 链接：https://leetcode-cn.com/problems/reorder-list
 */
public class RearrangeLink_143 {

    /**
     * 1) 寻找中间节点
     * 2）后半链表翻转
     * 3）前半连接后半
     * @param head
     */
    public void reorderList(ListNode head) {
        if (null == head) {
            return;
        }

        ListNode slow = middle(head);

        ListNode right = reverse(slow.next);

        // 截断链表
        slow.next = null;

        ListNode left = head;
        while (null != right) {
            ListNode next = right.next;
            right.next = left.next;
            left.next = right;

            right = next;
            left = left.next.next;
        }
    }

    /**
     * 翻转链表
     * @param head
     * @return
     */
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (null != head) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    /**
     * 寻找中间节点
     * @param head
     * @return
     */
    private ListNode middle(final ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;

        while (null != fast && null != fast.next) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
