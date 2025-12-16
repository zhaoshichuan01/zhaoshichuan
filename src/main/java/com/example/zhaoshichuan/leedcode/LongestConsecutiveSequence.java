package com.example.zhaoshichuan.leedcode;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {
        // 1. 边界处理：空数组直接返回0
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 2. 构建哈希集合，去重+O(1)时间查找
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int maxLength = 0; // 记录最长连续序列长度

        // 3. 遍历集合中的每个数
        for (int num : numSet) {
            // 关键：判断当前数是否是序列起点（num-1不在集合中）
            if (!numSet.contains(num - 1)) {
                int currentNum = num; // 当前遍历的数（从起点开始）
                int currentLength = 1; // 当前序列长度（至少包含起点）

                // 4. 向后查找连续的数，直到找不到为止
                while (numSet.contains(currentNum + 1)) {
                    currentNum++; // 往后走一位
                    currentLength++; // 序列长度+1
                }

                // 5. 更新最长序列长度
                maxLength = Math.max(maxLength, currentLength);
            }
        }

        return maxLength;
    }

    // 测试用例
    public static void main(String[] args) {
        LongestConsecutiveSequence solution = new LongestConsecutiveSequence();
        int[] nums = {100, 4, 200, 1, 3, 2};
        System.out.println(solution.longestConsecutive(nums)); // 输出4

        // 额外测试边界情况
        int[] nums2 = {};
        System.out.println(solution.longestConsecutive(nums2)); // 输出0

        int[] nums3 = {0, -1, 1};
        System.out.println(solution.longestConsecutive(nums3)); // 输出3
    }
}
