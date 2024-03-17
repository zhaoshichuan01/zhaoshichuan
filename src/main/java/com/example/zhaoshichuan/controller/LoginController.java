package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.PageBean;
import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.service.EmpService;
import com.example.zhaoshichuan.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 登录成功,服务端下发JWt令牌，之后前端每次请求都会携带这个令牌(一般在请求头中)，后端统一拦截每次请求，校验令牌有效性；
        // 如果有效，可以执行对应操作，如果令牌失效或者被篡改，则不允许执行对应操作
        Map<String,Object> claim = new HashMap<>();
        claim.put("id",emp.getId());
        claim.put("username",emp.getName());
        claim.put("password",emp.getUsername());
        String jwt =  JwtUtils.generateJwt(claim);
        return Result.success(jwt);
    }
}
