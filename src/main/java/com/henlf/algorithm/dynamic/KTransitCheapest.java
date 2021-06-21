package com.henlf.algorithm.dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * K 站中转内最便宜的航班：
 * 现在有n个城市，分别用0,1…,n - 1这些序号表示，城市之间的航线用三元组[from, to, price]来表示，
 * 比如说三元组[0,1,100]就表示，从城市0到城市1之间的机票价格是 100 元。
 *
 * 正整数n代表城市个数，数组flights装着若干三元组代表城市间的航线及价格，城市编号src代表你所在的城市，
 * 城市编号dst代表你要去的目标城市，整数K代表你最多经过的中转站个数。
 *
 * s1, s2是指向dst的相邻节点，我只要能够在K - 1步之内从src到达s1, s2，那我就可以在K步之内从src到达dst。
 *
 * 也就是如下关系式：
 *
 * dp(dst, k) = min(
 *     dp(s1, k - 1) + w1,
 *     dp(s2, k - 1) + w2
 * )
 * @author tanghongfeng
 * @date 2021-06-17 09:24
 */
public class KTransitCheapest {
    // 哈希表记录每个点的入度
    // to -> [from, price]
    private HashMap<Integer, List<int[]>> indegree;
    private int src, dst;

    // 备忘录
    private int[][] memo;

    /**
     * 会产生重叠子问题
     * @param n 节点个数
     * @param flights 航班线路
     * @param src 起点
     * @param dst 终点
     * @param K 最多中转次数
     * @return
     */
    public int findCheapestPriceRecursive(int n, int[][] flights, int src, int dst, int K) {
        // 将中转站个数转化成边的条数
        K++;
        this.src = src;
        this.dst = dst;

        // 需要知道s1, s2是指向dst的相邻节点，他们之间的权重是w1, w2。
        // 所以希望给一个节点，就能知道有谁指向这个节点，还知道它们之间的权重
        indegree = new HashMap<>();
        for (int[] f : flights) {
            int from = f[0];
            int to = f[1];
            int price = f[2];
            // 记录谁指向该节点，以及之间的权重
            indegree.putIfAbsent(to, new LinkedList<>());
            indegree.get(to).add(new int[] {from, price});
        }

        return dpRecursive(dst, K);
    }

    // 定义：从 src 出发，k 步之内到达 s 的最短路径权重
    int dpRecursive(int s, int k) {
        // base case
        if (s == src) {
            return 0;
        }
        if (k == 0) {
            return -1;
        }
        // 初始化为最大值，方便等会儿取最小值
        int res = Integer.MAX_VALUE;
        if (indegree.containsKey(s)) {
            // 当 s 有入度节点时，分解为子问题
            for (int[] v : indegree.get(s)) {
                int from = v[0];
                int price = v[1];
                // 从 src 到达相邻的入度节点所需的最短路径权重
                int subProblem = dpRecursive(from, k - 1);
                // 跳过无解的情况
                if (subProblem != -1) {
                    res = Math.min(res, subProblem + price);
                }
            }
        }

        // 如果还是初始值，说明此节点不可达
        return res == Integer.MAX_VALUE ? -1 : res;
    }


    public int findCheapestPriceMemo(int n, int[][] flights, int src, int dst, int K) {
        K++;
        this.src = src;
        this.dst = dst;
        // 初始化备忘录，全部填一个特殊值
        memo = new int[n][K + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -888);
        }

        // 需要知道s1, s2是指向dst的相邻节点，他们之间的权重是w1, w2。
        // 所以希望给一个节点，就能知道有谁指向这个节点，还知道它们之间的权重
        indegree = new HashMap<>();
        for (int[] f : flights) {
            int from = f[0];
            int to = f[1];
            int price = f[2];
            // 记录谁指向该节点，以及之间的权重
            indegree.putIfAbsent(to, new LinkedList<>());
            indegree.get(to).add(new int[] {from, price});
        }

        return dp(dst, K);
    }

    // 定义：从 src 出发，k 步之内到达 s 的最小成本
    int dp(int s, int k) {
        // base case
        if (s == src) {
            return 0;
        }
        if (k == 0) {
            return -1;
        }
        // 查备忘录，防止冗余计算
        if (memo[s][k] != -888) {
            return memo[s][k];
        }

        // 初始化为最大值，方便等会儿取最小值
        int res = Integer.MAX_VALUE;
        if (indegree.containsKey(s)) {
            // 当 s 有入度节点时，分解为子问题
            for (int[] v : indegree.get(s)) {
                int from = v[0];
                int price = v[1];
                // 从 src 到达相邻的入度节点所需的最短路径权重
                int subProblem = dp(from, k - 1);
                // 跳过无解的情况
                if (subProblem != -1) {
                    res = Math.min(res, subProblem + price);
                }
            }
        }

        // 存入备忘录
        memo[s][k] = res == Integer.MAX_VALUE ? -1 : res;
        return memo[s][k];
    }
}
