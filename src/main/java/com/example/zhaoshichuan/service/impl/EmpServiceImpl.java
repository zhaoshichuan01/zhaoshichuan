package com.example.zhaoshichuan.service.impl;


import com.example.zhaoshichuan.mapper.EmpMapper;
import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.PageBean;
import com.example.zhaoshichuan.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        Long total =  empMapper.count();
        List<Emp> rows = empMapper.page((page-1)*pageSize,pageSize);
        return new PageBean(total,rows);
    }
}
