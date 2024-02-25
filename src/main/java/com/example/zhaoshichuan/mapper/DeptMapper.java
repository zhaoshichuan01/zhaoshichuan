package com.example.zhaoshichuan.mapper;

import com.example.zhaoshichuan.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface DeptMapper {

    /**
     * 查询全部部门数据
     * */
    @Select("select * from dept")
    List<Dept> list();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept (name, create_time, update_time) values(#{name},#{createTime}, #{updateTime})")
    void insert(Dept dept);
}
