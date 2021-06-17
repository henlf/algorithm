package com.henlf.algorithm.sum;

import java.util.*;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，
 * 使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *
 * 链接：https://leetcode-cn.com/problems/3sum
 */
public class ThreeSum_15 {
    /**
     * 通过 Set 的唯一性来区别重复
     * 双指针
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        List<List<Integer>> results = new ArrayList<>(10);
        Set<String> combine = new HashSet<>();
        for (int i = 1; i <= nums.length - 2; i++) {
            int left = i;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i - 1] + nums[left] + nums[right];
                if (sum == 0) {
                    String str = String.valueOf(Math.abs(nums[i - 1])) +
                            Math.abs(nums[left]) +
                            Math.abs(nums[right]);
                    if (!combine.contains(str)) {
                        results.add(Arrays.asList(nums[i - 1], nums[left], nums[right]));
                        combine.add(str);
                    }

                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return results;
    }

    /**
     * 首先对数组进行排序，排序后固定一个数 nums[i]，再使用左右指针指向 nums[i]后面的两端，
     * 数字分别为 nums[L] 和 nums[R]，计算三个数的和 sum 判断是否满足为 0，满足则添加进结果集。
     *
     * 如果 nums[i]大于 0，则三数之和必然无法等于 0，结束循环
     * 如果 nums[i] == nums[i-1]，则说明该数字重复，会导致结果重复，所以应该跳过
     * 当 sum == 0 时，nums[L] == nums[L+1] 则会导致结果重复，应该跳过，L++
     * 当 sum == 0 时，nums[R] == nums[R-1] 则会导致结果重复，应该跳过，R--
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum_2(int[] nums) {
        if (null == nums || nums.length <= 0) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int negative = 0 - nums[i];
                int low = i + 1, high = nums.length - 1;
                while (low < high) {
                    if (nums[low] + nums[high] == negative) {
                        List<Integer> tuple = Arrays.asList(nums[i], nums[low], nums[high]);
                        results.add(tuple);

                        while (low < high && nums[low] == nums[low + 1]) {
                            low++;
                        }

                        while (low < high && nums[high] == nums[high - 1]) {
                            high--;
                        }

                        low++;
                        high--;
                    } else if (nums[low] + nums[high] > negative) {
                        --high;
                    } else {
                        ++low;
                    }
                }
            }
        }

        return results;
    }
}
