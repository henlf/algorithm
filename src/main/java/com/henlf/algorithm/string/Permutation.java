package com.henlf.algorithm.string;

import java.util.*;

public class Permutation {
    public String[] permutation(String s) {
        if (null == s || s.length() <= 0) {
            return null;
        }

        if (s.length() == 1) {
            return new String[]{s};
        }

        List<String> res = new ArrayList<>();
        permute(s.toCharArray(), 0, res);

        return res.toArray(new String[0]);
    }

    // abc
    private void permute(char[] chars, int start, List<String> res) {
        if(start == chars.length - 1) {
            res.add(String.valueOf(chars)); // 添加排列方案
            return;
        }

        Set<Character> duplicate = new HashSet<>();
        for (int i = start; i < chars.length; i++) {
            if (duplicate.contains(chars[i])) {
                continue;
            }

            duplicate.add(chars[i]);

            swap(chars, i, start); // 交换，将 c[i] 固定在第 start 位
            permute(chars, start + 1, res); // 开启固定第 x + 1 位字符
            swap(chars, i, start); // 恢复交换
        }

    }

    private void swap(char[] chars, int srcIndex, int changedIndex) {
        char ch = chars[srcIndex];
        chars[srcIndex] = chars[changedIndex];
        chars[changedIndex] = ch;
    }
}
