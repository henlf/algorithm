package com.henlf.algorithm.dynamic;

/**
 * <p>
 *     解决两个字符串的动态规划问题，一般都是用两个指针i,j分别指向两个字符串的最后，然后一步步往前走，缩小问题的规模。
 * </p>
 *
 *  编辑距离
 * @author tanghongfeng
 * @date 2021-06-28 08:45
 */
public class EditDistance {
    public static int minEditDistance(String s1, String s2) {
        return dp(s1.toCharArray(), s2.toCharArray(), s1.length() - 1, s2.length() -1);
    }
    /**
     * 动态规划求解最小编辑距离
     * @param i 字符串的末尾索引
     * @param j 字符串的末尾索引
     * @return 返回 s1[0..i] 和 s2[0..j] 的最小编辑距离
     */
    public static int dp(char[] s1, char[] s2, int i, int j) {
        // base case
        if (i == -1) {
            return j + 1;
        }

        if (j == -1) {
            return i + 1;
        }

        if (s1[i] == s2[j]) {
            /*
                s1[0..i] 和 s2[0..j] 的最小编辑距离等于 s1[0..i-1] 和 s2[0..j-1] 的最小编辑距离,
                也就是说 dp(i, j) 等于 dp(i-1, j-1)
             */
            return dp(s1, s2, i - 1, j - 1);
        } else {
            /*
                dp(i, j - 1) + 1 => 插入
                    直接在 s1[i] 插入一个和 s2[j] 一样的字符,
                    那么 s2[j] 就被匹配了，前移 j，继续跟 i 对比
                dp(i - 1, j) + 1 => 删除
                    直接把 s[i] 这个字符删掉，前移 i，继续跟 j 对比
                dp(i - 1, j - 1) + 1 => 替换
                    直接把 s1[i] 替换成 s2[j]，这样它俩就匹配了, 同时前移 i，j 继续对比
             */
            return min(dp(s1, s2, i, j - 1) + 1, // 插入
                        dp(s1, s2, i - 1, j) + 1, // 删除
                        dp(s1, s2, i - 1, j - 1) + 1); // 替换
        }
    }

    /**
     * 一般来说，处理两个字符串的动态规划问题，都是按本文的思路处理，建立 DP table。为什么呢，因为易于找出状态转移的关系
     * @param s1
     * @param s2
     * @return
     */
    public static int minDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int dp[][] = new int[m + 1][n + 1];

        // base case
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        // 自底向上求解
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i][j - 1] + 1,
                                    dp[i - 1][j] + 1,
                                    dp[i - 1][j - 1] + 1);
                }
            }
        }

        return dp[m][n];
    }

    private static int min(int i, int j, int k) {
        return Math.min(i, Math.min(j, k));
    }
}
