package com.example.zhaoshichuan;

import com.example.zhaoshichuan.mapper.EmpMapper;
import com.example.zhaoshichuan.mapper.UserMapper;
import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.User;
import org.apache.ibatis.annotations.Options;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ZhaoshichuanApplicationTests {

    @Autowired
    EmpMapper empMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testDelete() {
        int delete = empMapper.delete(17);
        System.out.println(delete);
    }


    @Test
    public void testquery() {
        List<User> res = userMapper.list();
        res.forEach(System.out::println);
    }


    @Test
    public void testInsert() {
        Emp emp = new Emp();
        emp.setUsername("Tom2");
        emp.setName("汤姆");
        emp.setImage("1.jpg");
        emp.setGender((short) 1);
        emp.setJob((short) 1);
        emp.setEntrydate(LocalDate.of(2000, 1, 1));
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDeptId(1);
        empMapper.insert(emp);
        System.out.println(emp.getId());
    }

    @Test
    public void testUpdate() {
        //构造员工对象
        Emp emp = new Emp();
        emp.setId(18);
        emp.setUsername("Tom1");
        emp.setName("汤姆1");
        emp.setImage("1 .jpg");
        emp.setGender((short)1);
        emp.setJob((short)1);
        emp.setEntrydate(LocalDate.of(2000,1,1));
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDeptId(1);
        empMapper.update(emp);

    }

}
