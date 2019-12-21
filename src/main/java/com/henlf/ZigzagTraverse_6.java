package com.henlf;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 *
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 *
 * 链接：https://leetcode-cn.com/problems/zigzag-conversion
 */
public class ZigzagTraverse_6 {
    /**
     * 按 Z 字形遍历，元素放入正确的行
     * @param str
     * @param numRows
     * @return
     */
    public static String convert(String str, int numRows) {
        if (null == str || str.length() < 2 || numRows < 2) {
            return str;
        }

        List<StringBuilder> result = new ArrayList<>();
        for (int i = 0; i < numRows; i++) { // 初始化
            result.add(new StringBuilder());
        }

        int i = 0;
        int flag = -1;
        for (char c : str.toCharArray()) {
            result.get(i).append(c);
            if (i == 0 || i == numRows - 1) {
                flag = - flag;
            }

            i += flag;
        }

        return String.join("", result);
    }
}
