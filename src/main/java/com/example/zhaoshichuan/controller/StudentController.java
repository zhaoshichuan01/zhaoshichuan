package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.pojo.Result;
import com.example.zhaoshichuan.pojo.Student;
import com.example.zhaoshichuan.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 查询所有学生
     */
    @GetMapping
    public Result list() {
        log.info("查询所有学生");
        List<Student> studentList = studentMapper.list();
        return Result.success(studentList);
    }

    /**
     * 根据ID查询学生
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询学生: {}", id);
        Student student = studentMapper.getById(id);
        return Result.success(student);
    }

    /**
     * 新增学生
     */
    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("新增学生: {}", student);
        studentMapper.insert(student);
        return Result.success();
    }

    /**
     * 修改学生信息
     */
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("修改学生信息: {}", student);
        studentMapper.update(student);
        return Result.success();
    }

    /**
     * 根据ID删除学生
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除学生: {}", id);
        studentMapper.deleteById(id);
        return Result.success();
    }
}