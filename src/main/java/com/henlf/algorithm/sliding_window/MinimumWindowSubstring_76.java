package com.henlf.algorithm.sliding_window;

import java.util.HashMap;

/**
 * <p>
 *     给定一个字符串 S、一个字符串 T，请在字符串 S 里找出：包含字符串 T 所有字母的最小子串。
 * </p>
 * <pre>
 *     输入：S = ADOBECODEBANC, T = ABC
 *     输出：BANC
 * </pre>
 *
 * @author tanghongfeng
 * @date 2021-11-04 08:43
 */
public class MinimumWindowSubstring_76 {
    public static String minWindow(String s, String t) {
        final HashMap<Character, Integer> need = new HashMap<>();
        final HashMap<Character, Integer> window = new HashMap<>();

        final char[] chars = t.toCharArray();
        for (Character character : chars) {
            need.put(character, need.getOrDefault(character, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int valid = 0;

        // 记录最小覆盖子串的起始索引及长度
        int start = 0;
        int len = Integer.MAX_VALUE;

        while (right < s.length()) {
            final char c = s.charAt(right++);

            // 进行窗口内数据的一系列更新
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);

                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 判断左侧窗口是否要收缩
            while (valid == need.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                // d 是将移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;
                // 进行窗口内数据的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }

                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }

        // 返回最小覆盖子串
        return len == Integer.MAX_VALUE ?
                "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
    }
}
