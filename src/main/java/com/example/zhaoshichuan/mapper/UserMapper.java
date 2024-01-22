package com.example.zhaoshichuan.mapper;


import com.example.zhaoshichuan.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // 自动生成该接口的实现类，并交给ioc容器
public interface UserMapper {

    @Select("select * from user")
    public List<User> list();
}
