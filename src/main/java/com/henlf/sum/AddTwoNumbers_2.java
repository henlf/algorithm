package com.henlf.sum;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。
 * 其中，它们各自的位数是按照 逆序 的方式存储的，
 * 并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 */
public class AddTwoNumbers_2 {
    /**
     * 遍历链表，逐步变更
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (null == l1 && null == l2) {
            return null;
        }

        if (null == l1) {
            return l2;
        }

        if (null == l2) {
            return l1;
        }

        ListNode sumHead = null;
        ListNode sumTail = null;
        ListNode current_1 = l1;
        ListNode current_2 = l2;

        int lastCarryBit = 0; // 上一节点的进位值
        while (null != current_1 && null != current_2) { // 共同节点
            int value = current_1.val + current_2.val + lastCarryBit;

            // 节点值
            int val = value % 10;
            // 进位
            int carryBit = value / 10;
            if (sumHead == null) {
                sumHead = new ListNode(val);
                sumTail = sumHead;
            } else {
                ListNode newNode = new ListNode(val);
                sumTail.next = newNode;
                sumTail = newNode;

            }

            lastCarryBit = carryBit;
            current_1 = current_1.next;
            current_2 = current_2.next;
        }

        if (null == current_1 && null == current_2) { // 两个链表都遍历完
            if (0 == lastCarryBit) {
                return sumHead;
            }

            // 最后节点还有进位值
            ListNode newNode = new ListNode(lastCarryBit);
            sumTail.next = newNode;
            sumTail = newNode;
            return sumHead;
        }

        if (null == current_1) { // 另一个链表还有节点
            return remain(sumHead, sumTail, current_2, lastCarryBit);
        }

        // 另一个链表还有节点
        return remain(sumHead, sumTail, current_1, lastCarryBit);
    }

    private ListNode remain(ListNode sumHead, ListNode sumTail, ListNode current, int lastCarryBit) {
        while (null != current) {
            int value = current.val + lastCarryBit;
            // 节点值
            int val = value % 10;
            // 进位
            int carryBit = value / 10;

            current.val = val;
            sumTail.next = current;
            sumTail = current;

            lastCarryBit = carryBit;
            current = current.next;
        }

        if (0 == lastCarryBit) {
            return sumHead;
        }

        // 最后节点还有进位值
        ListNode newNode = new ListNode(lastCarryBit);
        sumTail.next = newNode;
        sumTail = newNode;
        return sumHead;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
