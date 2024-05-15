package com.example.zhaoshichuan.learnSkills;

public class Dog extends Animal{
    @Override
    public void eat() {
        System.out.println("The dog eats dog food.");
    }

    @Override
    public void makeSound() {
        System.out.println("The dog barks.");
    }

    public void makeSound3() {
        System.out.println("The dog barks3.");
    }

    public void animalMakeSound() {
        super.makeSound();
    }
}
