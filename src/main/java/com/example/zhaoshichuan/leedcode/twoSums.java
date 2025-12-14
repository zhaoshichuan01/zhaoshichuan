package com.example.zhaoshichuan.leedcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class twoSums {
    // https://leetcode-cn.com/problems/two-sum/
    public int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++){
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] {};

    }

    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i<nums.length;i++){
            int key = target - nums[i];
            if (map.containsKey(key)){
                return new int[]{map.get(key), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }


    public static void main(String[] args) {
        twoSums twoSums = new twoSums();
        System.out.println(Arrays.toString(twoSums.twoSum2(new int[]{2,1,7,15}, 9)));
    }

}
