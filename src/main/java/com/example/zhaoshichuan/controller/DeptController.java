package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.pojo.Dept;
import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * 部门管理Controller
 */
@RestController // 组合注解，包括@responseBody,会把返回数据 封装为JSON格式
@Slf4j
public class DeptController {

    // private static Logger log = Logger.getLogger(DeptController.class.getName());
    // 等同于@Slf4j

    @Autowired
    private DeptService deptService;
    @RequestMapping(value = "/depts", method = RequestMethod.GET) // 指定请求方式是GET
    public Result list(){
        log.info("\n====查询全部部门数据===");
        List<Dept> deptList =  deptService.list();
        return Result.success(deptList);
    }

    @DeleteMapping("depts/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("\n====根据id删除部门数据，id：{}===",id);
        deptService.deleteById(id);
        return Result.success();
    }

    @PostMapping("depts")
    public  Result add(@RequestBody Dept dept) {
        log.info("\n====新增部门数据:{}===",dept);
        deptService.add(dept);
        return Result.success();
    }
}
