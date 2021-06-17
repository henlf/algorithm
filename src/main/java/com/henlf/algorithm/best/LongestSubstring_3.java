package com.henlf.algorithm.best;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 */
public class LongestSubstring_3 {
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int index = 0;

        if (s.length() == 1) {
            maxLength = s.length();
            index = 0;
        }

        // abcabcbb
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                if (j == s.length() || isDuplicate(s, i, j)) {
                    int length = j - i;
                    if (maxLength < length) {
                        maxLength = length;
                        index = i;
                    }

                    break;
                }
            }
        }

        System.out.println("max length is: " + maxLength);
        System.out.println("substring is: " + s.substring(index, index + maxLength));

        return maxLength;
    }

    private boolean isDuplicate(String s, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (s.charAt(end) == s.charAt(i)) {
                return true;
            }
        }

        return false;
    }
}
