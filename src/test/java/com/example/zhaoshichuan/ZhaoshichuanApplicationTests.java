package com.example.zhaoshichuan;

import com.example.zhaoshichuan.mapper.EmpMapper;
import com.example.zhaoshichuan.mapper.UserMapper;
import com.example.zhaoshichuan.pojo.Emp;
import com.example.zhaoshichuan.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.ibatis.annotations.Options;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class ZhaoshichuanApplicationTests {

    @Autowired
    EmpMapper empMapper;
    @Autowired
    UserMapper userMapper;

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
        // 返回主键id
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

    @Test
    public void testSelectById(){
        Emp emp =  empMapper.getById(20);
        System.out.println(emp);
        //mybatis机制：实体类的属性名与表中的字段名不一致，则会返回null，可使用@Result注解或者开启mybatis的驼峰命名自动映射开关
        //Emp(id=20, username=Tom2, password=123456, name=汤姆, gender=1, image=1.jpg, job=1, entrydate=2000-01-01, deptId=null, createTime=null, updateTime=null)
    }

    @Test
    public void testList(){
        List<Emp> res = empMapper.list("张", (short) 1, LocalDate.of(2010,1,1), LocalDate.of(2020,1,1));
        System.out.println(res);
    }

    @Test
    public void testListXml(){
        //List<Emp> res = empMapper.listXml("张", (short) 1, LocalDate.of(2010,1,1), LocalDate.of(2020,1,1));
        List<Emp> res = empMapper.listXml("张", (short) 1,null,null);
        System.out.println(res);
    }

    @Test
    public void testUpdateXml(){
        Emp emp = new Emp();
        emp.setId(20);
        emp.setName("汤姆11");
        empMapper.update2(emp);
    }


    @Test
    public void testUDeleteXml(){
       List<Integer> ids = Arrays.asList(16,17,18);
        empMapper.batchDeleteById(ids);
    }

    /**
     * 审生成jwt 令牌，基于base64编码： https://www.jyshare.com/front-end/693/
     * */
    @Test
    public void testGenToken(){
        Map<String,Object> claim = new HashMap<>();
        claim.put("id",1);
        claim.put("name","tom");
        String jwt =  Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itheima") //签名算法
                .setClaims(claim)  //载荷,自定义部分
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 令牌有效期1小时
                .compact();
        System.out.println(jwt);
    }

    /**
     * 解析Jwt令牌
     * */
    @Test
    public void testParseJwt(){
        Claims claim =  Jwts.parser()
                .setSigningKey("itheima")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcxMDY1MjA0Mn0.DbynlMLz1NJYXYIz8_omUtODVLWjApzY44mtwKl4--Q")
                .getBody();
        System.out.println(claim);
    }
}
