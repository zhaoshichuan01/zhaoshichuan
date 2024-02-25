package com.example.zhaoshichuan.service;

import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.PageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {

    PageBean page(Integer page, Integer pageSize,
                  String name, Short gender, LocalDate begin, LocalDate end);
}
