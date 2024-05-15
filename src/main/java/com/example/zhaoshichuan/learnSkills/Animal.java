package com.example.zhaoshichuan.learnSkills;

public abstract class Animal {

    // 抽象方法
    public abstract void eat();

    // 非抽象方法
    public void makeSound() {
        System.out.println("The animal makes a sound.");
    }

    public void makeSound2() {
        System.out.println("The animal makes a sound2.");
    }
}
