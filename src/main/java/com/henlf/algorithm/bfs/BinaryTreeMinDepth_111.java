package com.henlf.algorithm.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树最小深度
 *
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量
 *
 * @author tanghongfeng
 * @date 2021-06-18 09:06
 */
public class BinaryTreeMinDepth_111 {
    int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        // root 本身就是一层，depth 初始化为 1
        int depth = 1;

        while (!q.isEmpty()) {
            int sz = q.size();
            /* 将当前队列中的所有节点向四周扩散 */
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                /* 判断是否到达终点 */
                if (cur.left == null && cur.right == null) {
                    return depth;
                }

                /* 将 cur 的相邻节点加入队列 */
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }

            /* 这里增加步数 */
            depth++;
        }

        return depth;
    }

    static class TreeNode {
        private TreeNode left;
        private TreeNode right;
    }
}


