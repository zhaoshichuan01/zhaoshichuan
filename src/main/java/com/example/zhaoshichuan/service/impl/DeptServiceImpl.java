package com.example.zhaoshichuan.service.impl;


import com.example.zhaoshichuan.mapper.DeptMapper;
import com.example.zhaoshichuan.pojo.Dept;
import com.example.zhaoshichuan.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }
}

