package com.example.zhaoshichuan.service;

import com.example.zhaoshichuan.pojo.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {

    /**
     * 查询全部部分信息
     * */
    List<Dept> list();
}
