package com.example.zhaoshichuan;

import java.util.*;

public class lianxi {
    public static void main(String[] args) {
        List<Integer> aa = new ArrayList<>();
        // ArrayList<Integer> bb = new ArrayList<>();
        aa.add(1);
        aa.remove("1");
        HashMap<Integer, String> aaaa = new HashMap<>();
        Map<Integer, Integer> bbb = new HashMap<>();

        int[] nums = new int[] { 2, 3, 4, 5, 3 };
        int[][] nums2 = new int[][] { { 2, 3, 4 }, { 4, 5 } };
        System.out.println(nums.toString());
        System.out.println(Arrays.toString(nums));

        System.out.println(nums2.toString());
        System.out.println(Arrays.toString(nums2));

        System.out.println(Arrays.deepToString(nums2));

        int[] kk = new int[8];

        System.out.println(kk);
        System.out.println(Arrays.toString(kk));

        List aaa = Arrays.asList(1,2,3,4);
        System.out.println(aaa);
        //System.out.println(Arrays.toString(aaa));

        findRepeatNumber(nums);
        System.out.println("Hello World!");
        String sb = new String("abc");
        String ss = "abc";
        System.out.println(Arrays.toString(sb.toCharArray()));
    }

    public static int findRepeatNumber(int[] nums) {
        Set<Integer> dic = new HashSet<>();
        // HashSet<Integer> dic2 = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (dic.contains(nums[i])) {
                return nums[i];
            }
            dic.add(nums[i]);
        }
        return -1;

    }

}

// 基本数据类型：
// 布尔型：Boolean
// 数型：byte1 short 2.int 4.long8
// 字符型：char
// 浮点型：float double

// 引用数据类型：数组、接口、类，String

// 八种基本类型都有自己对应的包装类，分别为：
// 布尔类型：Boolean
// 字符类型：Character
// 整数类型：Byte、Short、Integer、Long
// 浮点类型：Float、Double


// java.util.Arrays: https://blog.csdn.net/zch981964/article/details/130785726

// 如何查看Java和Javac版本？
// 在cmd中输入
// java -version //查看Java版本
// javac -version //查看Javac版本

// Java--Java版本和JDK版本   https://blog.csdn.net/MinggeQingchun/article/details/120578602
