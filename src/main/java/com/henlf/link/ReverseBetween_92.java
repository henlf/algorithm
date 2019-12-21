package com.henlf.link;

/**
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 *
 * 说明:
 * 1 ≤ m ≤ n ≤ 链表长度。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 *
 * 链接：https://leetcode-cn.com/problems/reverse-linked-list-ii
 */
public class ReverseBetween_92 {
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode res = new ListNode(0);
        res.next = head;

        ListNode start = res;

        // 需要反转的那一段的上一个节点
        int index = 1;
        while (index < m) {
            start = start.next;
            index++;
        }

        // 翻转
        ListNode prev = null;
        ListNode reversedHead = start.next;
        ListNode next = null;
        for (int i = m; i <= n; i++) {
            next = reversedHead.next;
            reversedHead.next = prev;
            prev = reversedHead;
            reversedHead = next;
        }

        start.next = prev;
        // 找到反转这段的最后一个节点。
        while (prev.next != null) {
            prev = prev.next;
        }

        prev.next = next;

        return res.next;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
