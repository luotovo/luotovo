package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
/*
 员工信息
 */

@Mapper
public interface EmpMapper {

    /*--------------------------------原始分页查询--------------------------------*
     查询员工总记录数
     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id ")
//    public Long count();
    /*


     分页查询
     */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc " +
//            "limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);





    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id ")
    public Long count();
    // sql语句最后不能加；否则会拦截后面的语句
    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc ")

//    public List<Emp> list(String name, Integer gender,
//                          LocalDate begin, LocalDate end);
    public List<Emp> list(EmpQueryParam empQueryParam);

    /*
     新增员工
     */

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)"+
    "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime} )"
)
    void inset(Emp emp);

    /*
     根据id删除员工
     */
    void deleteByIds(List<Integer> ids);

    /*
     根据id查询员工信息,和经历信息

     */
    Emp getInfo(Integer id);
    /*
      更新员工信息
     */
    void updateById(Emp emp);

    /*
     条件分页查询
     */
    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    @Select("select id,username,name from emp where username = #{username} and password = #{password}")
    Emp selectByUernameAndPassword(Emp emp);
    @Select("SELECT id,name from  emp ")
    List<Emp> find(Integer empId, String name);
}




























