package com.example.zhaoshichuan.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private Short age;
    private Short gender;
    private String phone;

//    public User() {
//    }
//
//    public User(Integer id, String name, Short age, Short gender, String phone) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.phone = phone;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Short age) {
//        this.age = age;
//    }
//
//    public void setGender(Short gender) {
//        this.gender = gender;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Short getAge() {
//        return age;
//    }
//
//    public Short getGender() {
//        return gender;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", age=" + age +
//                ", gender=" + gender +
//                ", phone='" + phone + '\'' +
//                '}';
//    }
}
