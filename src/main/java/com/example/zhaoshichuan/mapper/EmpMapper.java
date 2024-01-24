package com.example.zhaoshichuan.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface EmpMapper {
    @Delete("delete from emp where id = #{id}")
    public int delete(Integer id);

}
