package com.henlf.algorithm.dynamic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>4 键盘问题</p>
 * <pre>
 *     假设有一个特殊的键盘，包含下面的案件：
 *     Key 1 : A : 在屏幕上打印一个 'A'
 *     Key 2 : Ctrl + A : 选中整个屏幕
 *     key 3 : Ctrl + C : 复制选中区域到缓冲区
 *     Key 4 : Ctrl + V : 将缓冲区的内容输出到上次输入结束的位置，并显示在屏幕上
 * </pre>
 * @author tanghongfeng
 * @date 2021-07-27 08:42
 */
public class FourKeymap {
    public int maxA(int count) {
        return dp(count, 0, 0);
    }

    /**
     * 这种思路会很容易理解，但是效率并不高，我们直接走流程：对于动态规划问题，首先要明白有哪些「状态」，有哪些「选择」。
     * 具体到这个问题，对于每次敲击按键，有哪些「选择」是很明显的：4 种，就是题目中提到的四个按键，分别是A、C-A、C-C、C-V（Ctrl简写为C）。
     * 接下来，思考一下对于这个问题有哪些「状态」？或者换句话说，我们需要知道什么信息，才能将原问题分解为规模更小的子问题？
     * 你看我这样定义三个状态行不行：第一个状态是剩余的按键次数，用n表示；第二个状态是当前屏幕上字符 A 的数量，用a_num表示；第三个状态是剪切板中字符 A 的数量，用copy表示。
     * 如此定义「状态」，就可以知道 base case：当剩余次数n为 0 时，a_num就是我们想要的答案。
     *
     * @param count
     * @return
     */
    private int dp(int count, Integer numsA, Integer copyNums) {
        if (count <= 0) {
            return numsA;
        }

        return max(
           dp(count - 1, numsA + 1, copyNums), // A
           dp(count - 1, numsA + copyNums, copyNums), // ctrl + v
           dp(count - 2, numsA, numsA) // ctrl + A, ctrl + C
        );
    }

    private int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}
