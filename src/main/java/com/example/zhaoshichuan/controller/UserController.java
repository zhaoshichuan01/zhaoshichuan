package com.example.zhaoshichuan.controller;


import com.example.zhaoshichuan.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

// 渲染 测试
@Controller
public class UserController {

    // 模拟数据库
    private List<User> userList = new ArrayList<>();

    // 显示注册表单页
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // 绑定空对象到表单
        return "register";
    }

    // 处理注册提交
    @PostMapping("/register")
    public String handleRegister(User user, Model model) {
        userList.add(user);
        model.addAttribute("message", "注册成功！");
        return "redirect:/user-list"; // 重定向到用户列表页
    }

    // 显示用户列表
    @GetMapping("/user-list")
    public String showUserList(Model model) {
        model.addAttribute("users", userList);
        return "user-list";
    }
}

