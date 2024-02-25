package com.example.zhaoshichuan.service;

import com.example.zhaoshichuan.pojo.Dept;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {

    /**
     * 查询全部部分信息
     * */
    List<Dept> list();

    void deleteById(Integer id);

    void add(Dept dept);
}
