package com.example.zhaoshichuan.service;

import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.PageBean;

import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {

    PageBean page(Integer page, Integer pageSize);
}
