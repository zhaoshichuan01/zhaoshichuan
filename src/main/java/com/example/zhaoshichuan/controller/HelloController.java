package com.example.zhaoshichuan.controller;


import com.example.zhaoshichuan.service.query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //古老用法
    //private query qq = new queryImp();
    @Autowired
    query query;


    @GetMapping("/hello")
    public String hello(){
        return "zhaoshichuan";
    }

    @GetMapping("/hello1")
    public String hello1(){
        String res =  query.list();
        return res;
    }


}
