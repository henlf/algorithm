package com.henlf.algorithm.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 优势洗牌
 * <p>
 *  输入两个长度相等的数组nums1和nums2，请你重新组织nums1中元素的位置，使得nums1的「优势」最大化。
 *
 *  如果nums1[i] > nums2[i]，就是说nums1在索引i上对nums2[i]有「优势」。
 *  优势最大化也就是说让你重新组织nums1，尽可能多的让nums[i] > nums2[i]。
 *
 *  <pre>
 *      比如输入：
 *
 *        nums1 = [12,24,8,32]
 *        nums2 = [13,25,32,11]
 *
 *        你的算法应该返回[24,32,8,12]，因为这样排列nums1的话有三个元素都有「优势」。
 *  </pre>
 * </p>
 * @author tanghongfeng
 * @date 2021-06-21 08:48
 */
public class AdvantagesShuffling_870 {
    /**
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        // 给 nums2 降序排序
        PriorityQueue<int[]> maxpq = new PriorityQueue<>(
                (int[] pair1, int[] pair2) -> pair2[1] - pair1[1]
        );

        for (int i = 0; i < n; i++) {
            maxpq.offer(new int[]{i, nums2[i]});
        }
        // 给 nums1 升序排序
        Arrays.sort(nums1);

        // nums1[left] 是最小值，nums1[right] 是最大值
        int left = 0, right = n - 1;
        int[] res = new int[n];

        while (!maxpq.isEmpty()) {
            int[] pair = maxpq.poll();
            // maxval 是 nums2 中的最大值，i 是对应索引
            int i = pair[0], maxval = pair[1];
            if (maxval < nums1[right]) {
                // 如果 nums1[right] 能胜过 maxval，那就自己上
                res[i] = nums1[right];
                right--;
            } else {
                // 否则用最小值混一下，养精蓄锐
                res[i] = nums1[left];
                left++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(advantageCount(new int[]{12, 24, 8, 32}, new int[]{13, 25, 32, 11})));
    }
}
