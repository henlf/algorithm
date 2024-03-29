package com.henlf.algorithm.dynamic;

import java.util.Arrays;

/**
 * 最长递增子序列（LIS）
 *
 * <p>给定一个无序的整数数组，找到其中最长上升子序列的长度</p>
 * 「子序列」和「子串」这两个名词的区别，子串一定是连续的，而子序列不一定是连续的。
 * @author tanghongfeng
 * @date 2021-07-26 08:59
 */
public class LongestIncreasingSubsequence {
    /**
     *
     * @return
     */
    public static int lis(int[] nums) {
        // dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
        int[] dp = new int[nums.length];

        // 将 dp 数组初始化为 1，因为至少包含自己
        Arrays.fill(dp, 1);

        // 计算 dp[0...i]
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // 取 dp 数组最大的值
        int res = 0;
        for (int i = 0; i < dp.length; i ++) {
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    /**
     * 使用二分法来解决 LIS 问题
     * @param nums
     * @return
     */
    public static int binarySearchOfLIS(int[] nums) {
        int[] top = new int[nums.length];

        // 堆牌初始化为 0
        int piles = 0;
        for (int i = 0; i < nums.length; i++) {
            // 要处理的扑克牌
            int poker = nums[i];

            // 探索左侧边界的 二分查找
            int left = 0;
            int right = piles;

            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            // 没找到合适的堆，新建一个堆
            if (left == piles) {
                piles++;
            }

            // 把当前牌放到堆顶
            top[left] = poker;
        }

        // 堆牌数就是 LIS 长度
        return piles;
    }

    public static void main(String[] args) {
        System.out.println(lis(new int[]{10, 9 , 2, 5, 3, 7, 101, 18}));
    }
}
