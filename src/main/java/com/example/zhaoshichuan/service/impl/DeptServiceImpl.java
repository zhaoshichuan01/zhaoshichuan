package com.example.zhaoshichuan.service.impl;


import com.example.zhaoshichuan.aop.MyLog;
import com.example.zhaoshichuan.mapper.DeptMapper;
import com.example.zhaoshichuan.mapper.EmpMapper;
import com.example.zhaoshichuan.pojo.Dept;
import com.example.zhaoshichuan.pojo.DeptLog;
import com.example.zhaoshichuan.service.DeptLogService;
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

    @Autowired
    private DeptLogService deptLogService;
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    //@Transactional // 只有出现runtimeException才会回滚
    @Transactional(rollbackFor = Exception.class) // 任何异常都会回滚
    @Override
    public void deleteById(Integer id) throws Exception {
        try {
            // 删除部门
            deptMapper.deleteById(id);
            // 模拟运行时异常
            int i = 1/0;
            // 模拟编译时异常，需要手动抛出
            // if (true){throw new Exception("删除失败");}
            // 删除部门下的员工
            empMapper.deleteByDempId(id);
        }finally {
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作,此次解散的是"+id+"号部门");
            deptLogService.insert(deptLog);
        }

    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);

    }

    @Override
    @MyLog
    public Dept getById(Integer id) {
        Dept res =  deptMapper.getById(id);
        return res;
    }
}

