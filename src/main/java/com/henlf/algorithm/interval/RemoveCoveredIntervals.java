package com.henlf.algorithm.interval;

import java.util.Arrays;

/**
 * <p>
 *     删除覆盖区间
 * </p>
 * @author tanghongfeng
 * @date 2021-11-16 11:07
 */
public class RemoveCoveredIntervals {
    public static int removeCoveredIntervals(int[][] intvs) {
        // 按照起点升序排列，起点相同时降序排列
        Arrays.sort(intvs, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        // 记录合并区间的起点和终点
        int left = intvs[0][0];
        int right = intvs[0][1];

        int res = 0;
        for (int i = 1; i < intvs.length; i++) {
            int[] intv = intvs[i];
            // 情况一，找到覆盖区间
            if (left <= intv[0] && right >= intv[1]) {
                res++;
            }
            // 情况二，找到相交区间，合并
            if (right >= intv[0] && right <= intv[1]) {
                right = intv[1];
            }
            // 情况三，完全不相交，更新起点和终点
            if (right < intv[0]) {
                left = intv[0];
                right = intv[1];
            }
        }

        return intvs.length - res;
    }

    public static void main(String[] args) {
        int[][] intervals = {
                {1, 4},
                {3, 6},
                {2, 8}
        };
        System.out.println(removeCoveredIntervals(intervals));
    }
}
