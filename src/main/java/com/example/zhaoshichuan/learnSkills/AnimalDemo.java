package com.example.zhaoshichuan.learnSkills;

public class AnimalDemo {

    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.makeSound();
        dog.animalMakeSound();
        dog.eat();
        System.out.println("*****************************************1");
        Animal a = new Animal() {
            @Override
            public void eat() {

            }
        };
        a.eat();
        a.makeSound2();
        a.makeSound();
        System.out.println("*****************************************2");
        Animal b = new Dog();
        b.eat();
        b.makeSound2();
        b.makeSound();
        System.out.println("*****************************************3");
        Dog c = new Dog();
        c.eat();
        c.makeSound2();
        c.makeSound();
        c.makeSound3();
    }
}
