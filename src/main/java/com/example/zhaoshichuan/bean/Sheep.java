package com.example.zhaoshichuan.bean;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "sheep")
public class Sheep {
    private Long id;
    private  String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sheep{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
