package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.PageBean;
import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工登录Controller
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestParam String name, @RequestParam String pwd){
        log.info("name:{}, pwd:{}",name,pwd);
        Emp emp = empService.login(name,pwd);
        if (emp == null){
            return Result.error("该用户名不存在");
        }
        if (! emp.getPassword().equals(pwd)){
            return Result.error("用户名或密码错误");
        }
        return Result.success(emp);
    }
}
