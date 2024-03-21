package com.example.zhaoshichuan.service.impl;


import com.example.zhaoshichuan.mapper.DeptMapper;
import com.example.zhaoshichuan.mapper.EmpMapper;
import com.example.zhaoshichuan.pojo.Dept;
import com.example.zhaoshichuan.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        // 删除部门
        deptMapper.deleteById(id);
        // 模拟异常
        int i = 1/0;
        // 删除员工
        empMapper.deleteByDempId(id);
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);

    }

    @Override
    public Dept getById(Integer id) {
        Dept res =  deptMapper.getById(id);
        return res;
    }
}

