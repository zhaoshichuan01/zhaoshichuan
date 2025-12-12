package com.example.zhaoshichuan.mapper;

import com.example.zhaoshichuan.pojo.Student;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    List<Student> list();

    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    @Insert("insert into student(name, gender, age, class, enroll_date) " +
            "values(#{name}, #{gender}, #{age}, #{className}, #{enrollDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Student student);

    @Update("update student set name=#{name}, gender=#{gender}, age=#{age}, " +
            "class=#{className}, enroll_date=#{enrollDate} where id=#{id}")
    void update(Student student);

    @Delete("delete from student where id = #{id}")
    void deleteById(Integer id);
}