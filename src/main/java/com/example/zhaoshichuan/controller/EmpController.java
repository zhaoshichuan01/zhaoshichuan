package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.PageBean;
import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理Controller
 */
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/emps")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate end) {
        PageBean res = empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(res);
    }
}
