package com.example.zhaoshichuan;

import com.example.zhaoshichuan.mapper.EmpMapper;
import com.example.zhaoshichuan.mapper.UserMapper;
import com.example.zhaoshichuan.pojo.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	public void testDelete(){
		int delete = empMapper.delete(17);
		System.out.println(delete);
	}


	@Test
	public void testquery(){
		List<User> res =  userMapper.list();
		res.forEach(System.out::println);
	}
}
