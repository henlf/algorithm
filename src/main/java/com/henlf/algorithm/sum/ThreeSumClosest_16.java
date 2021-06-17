package com.henlf.algorithm.sum;

import java.util.Arrays;

/**
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。
 * 找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
 *
 * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
 *
 * 链接：https://leetcode-cn.com/problems/3sum-closest
 */
public class ThreeSumClosest_16 {
    /**
     * 升序排序，指定一个不变值
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        if (null == nums || nums.length <= 0) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(nums);

        int closer = Integer.MAX_VALUE;
        int sumClosest = Integer.MAX_VALUE;
        for (int i = 1; i <= nums.length - 2; i++) { // 不变值为数组索引为 i - 1 的项
            // 双指针
            int left = i;
            int right = nums.length - 1;
            while (left < right) {
                int threeSum = nums[i - 1] + nums[left] + nums[right];
                int sum = target - (threeSum);
                if (Math.abs(sum) < Math.abs(closer)) { // 目标值更接近
                    closer = sum;
                    sumClosest = threeSum;
                }

                if (threeSum >= target) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        return sumClosest;
    }
}
