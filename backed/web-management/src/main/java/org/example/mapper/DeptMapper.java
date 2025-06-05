package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dept;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface DeptMapper {
    /*
    * 查询所有部门信息
     */

    //方式一：手动结果映射
//    @Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })

    //as 起别名
//    @Select("select id,name,create_time as createTime,update_time as updateTime from dept order by update_time desc ;")
    @Select("select id,name,create_time ,update_time  from dept order by update_time desc ;")

    List<Dept> findAll();

    /*
    * 根据id删除部门信息
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    /*
    * 添加部门
     */
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /*
    * 根据id查询部门信息
     */
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    /*
    * 修改部门信息
     */
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);

}
