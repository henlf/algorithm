package com.henlf;

import java.util.*;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 示例:
 *
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 */
public class LetterCombinations {
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() <= 0) {
            throw new IllegalArgumentException();
        }

        List<String> result = new ArrayList<>();
        char[] chars = digits.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                char first = chars[i];
                char second = chars[j];
            }
        }

        return result;
    }

    private Map<Integer, List<Character>> init() {
        Map<Integer, List<Character>> digitToChar = new HashMap<>(8);
        digitToChar.put(2, Arrays.asList('a', 'b', 'c'));
        digitToChar.put(3, Arrays.asList('d', 'e', 'f'));
        digitToChar.put(4, Arrays.asList('g', 'h', 'i'));
        digitToChar.put(5, Arrays.asList('j', 'k', 'l'));
        digitToChar.put(6, Arrays.asList('m', 'n', 'o'));
        digitToChar.put(7, Arrays.asList('p', 'q', 'r', 's'));
        digitToChar.put(8, Arrays.asList('t', 'u', 'v'));
        digitToChar.put(9, Arrays.asList('w', 'x', 'y', 'z'));

        return digitToChar;
    }
}
