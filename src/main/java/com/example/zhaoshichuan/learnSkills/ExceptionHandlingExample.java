package com.example.zhaoshichuan.learnSkills;

public class ExceptionHandlingExample {

    // 这个方法可能会抛出异常，但只是打印异常信息并没有重新抛出
    public static void methodThatMightThrowException() {
        try {
            // 假设这里有一些可能抛出异常的代码
            int result = 1/0; // 这会抛出一个ArithmeticException
        } catch (ArithmeticException e) {
            // 捕获异常并打印异常信息
            System.err.println("An arithmetic exception occurred: " + e.getMessage());
            // 注意：这里没有使用 throw 重新抛出异常
        }
        // 方法继续执行其他可能的代码...
        System.out.println("MethodThatMightThrowException continues execution...");
    }

    // 另一个方法调用可能会抛出异常的方法
    public static void callingMethod() {
        System.out.println("Calling method starts execution...");
        // 调用可能抛出异常的方法
        methodThatMightThrowException();

        // 调用者不知道异常已经发生，因为它没有被重新抛出
        // 这里的代码会继续执行，但可能基于一个错误的状态
        System.out.println("Calling method continues execution...");
    }

    public static void main(String[] args) {
        // 执行调用方法
        callingMethod();
        System.out.println("Main method continues execution...");

        // 主方法继续执行，但此时程序可能处于一个不一致的状态
        // 因为 methodThatMightThrowException 中的异常被静默处理了
    }
}
