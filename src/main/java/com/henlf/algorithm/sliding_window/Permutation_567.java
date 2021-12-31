package com.henlf.algorithm.sliding_window;

import java.util.HashMap;

/**
 * <p>
 *     给定两个字符串 S1 和 S2，写一个函数来判断 S2 是否包含 S1 的排列。
 * </p>
 * <pre>
 *     输入：S1 = "ab", S2 = "eidbaooo"
 *     输出：True
 * </pre>
 * @author tanghongfeng
 * @date 2021-11-08 08:52
 */
public class Permutation_567 {
    /**
     * 判断 s 中是否存在 t 的排列
     * @param t
     * @param s
     * @return
     */
    public static boolean checkInclusion(String t, String s) {
        final HashMap<Character, Integer> need = new HashMap<>();
        final HashMap<Character, Integer> window = new HashMap<>();

        final char[] chars = t.toCharArray();
        for (Character character : chars) {
            need.put(character, need.getOrDefault(character, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int valid = 0;

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
            while (right - left >= t.length()) {
                // 在这里判断是否找到了合法的子串
                if (valid == need.size()) {
                    return true;
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

        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo"));
    }
}
