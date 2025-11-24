package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.pojo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class TestController {

    /**
     * 处理前端页面访问路径
     */
    @GetMapping("/form-page")
    public Result formPage() {
        Map<String, String> pageData = new HashMap<>();
        pageData.put("title", "测试表单页面");
        pageData.put("description", "这是一个用于测试的表单页面");
        return Result.success(pageData);
    }

    /**
     * 提供测试数据的接口
     */
    @GetMapping("/test-data")
    public Result getTestData() {
        Map<String, Object> testData = new HashMap<>();
        testData.put("id", 1);
        testData.put("name", "张三");
        testData.put("email", "zhangsan@example.com");
        testData.put("message", "这是一条测试数据");
        testData.put("timestamp", System.currentTimeMillis());
        
        return Result.success(testData);
    }

    /**
     * 接收表单提交数据的接口
     */
    @PostMapping("/submit-form")
    public Result submitForm(@RequestBody Map<String, Object> formData) {
        // 在实际应用中，这里会处理表单数据，比如保存到数据库
        System.out.println("接收到表单数据: " + formData);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "表单提交成功");
        response.put("receivedData", formData);
        return Result.success(response);
    }
}