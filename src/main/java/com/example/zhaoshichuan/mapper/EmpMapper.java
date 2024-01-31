package com.example.zhaoshichuan.mapper;

import com.example.zhaoshichuan.pojo.Emp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    // @Delete("delete from emp where id = ${id}")
    // $会将参数拼接到sql语句中，#会使用预编译的方法，并不会拼接，可防止sql注入
    @Delete("delete from emp where id = #{id}")
    public int delete(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id") //返回插入这条数据的主键id
    @Insert("insert into emp(username,name,gender,image,job,entrydate,dept_id,create_time,update_time) " +
            "values(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    @Update("update emp set username=#{username},name=#{name},gender=#{gender},image=#{image}," +
            "job=#{job},entrydate=#{entrydate},dept_id=#{deptId},update_time=#{updateTime} where id=#{id}")
    public void update(Emp emp);


    @Select("select * from emp where id = #{id}")
    public Emp getById(Integer id);


    //条件查询员工
    @Select("select * from emp where name like '%${name}%' and gender = #{gender} and "
            + "entrydate between #{begin} and #{end} order by update_time desc ")
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    //xml配置
    public List<Emp> listXml(String name, Short gender, LocalDate begin, LocalDate end);


    //动态更新
    public void update2(Emp emp);


    public void batchDeleteById(List<Integer> ids);
}
