package com.henlf.algorithm.dynamic;

/**
 * https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484475&idx=1&sn=8e9518d67ae8f4c16f14fb0c4d584c79&chksm=9bd7fa33aca07325c056c017b7ff5b434a11fe7fee1a0c14aacbc9f1dd317bb7770cb1faef36&scene=178&cur_album_id=1318881141113536512#rd
 * @author tanghongfeng
 * @date 2021-07-26 10:12
 */
public class KMP {
    /*
        dp[j][c] = next
        0 <= j < M，代表当前的状态
        0 <= c < 256，代表遇到的字符（ASCII 码）
        0 <= next <= M，代表下一个状态

        dp[4]['A'] = 3 表示：
        当前是状态 4，如果遇到字符 A，
        pat 应该转移到状态 3

        dp[1]['B'] = 2 表示：
        当前是状态 1，如果遇到字符 B，
        pat 应该转移到状态 2
     */
    private int[][] dp;

    // 模式串
    private String pat;

    public KMP(String pat) {
        this.pat = pat;
        // 通过 pat 构建 dp 数组
        // 需要 O(M) 时间
        int M = pat.length();
        // dp[状态][字符] = 下个状态
        dp = new int[M][256];
        // base case
        dp[0][pat.charAt(0)] = 1;
        // 影子状态 X 初始为 0
        int X = 0;
        // 构建状态转移图（稍改的更紧凑了）
        for (int j = 1; j < M; j++) {
            for (int c = 0; c < 256; c++) {
                dp[j][c] = dp[X][c];
                dp[j][pat.charAt(j)] = j + 1;
                // 更新影子状态
                X = dp[X][pat.charAt(j)];
            }
        }
    }

    public int search(String txt) {
        // 借助 dp 数组去匹配 txt
        // 需要 O(N) 时间

        int M = pat.length();
        int N = txt.length();
        // pat 的初始态为 0
        int j = 0;
        for (int i = 0; i < N; i++) {
            // 当前是状态 j，遇到字符 txt[i]，
            // pat 应该转移到哪个状态？
            j = dp[j][txt.charAt(i)];

            // 如果达到终止态，返回匹配开头的索引
            if (j == M) return i - M + 1;
        }

        // 没到达终止态，匹配失败
        return -1;
    }

    public static void main(String[] args) {
        final KMP kmp = new KMP("abcd");
        System.out.println(kmp.search("xyzabdxabcd"));
    }
}
