package org.example.service;


import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.LoginInfo;
import org.example.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface EmpService{
//    PageResult<Emp> page(Integer page, Integer pageSize,
//                         String name, Integer gender, LocalDate begin, LocalDate end);
      PageResult<Emp> page(EmpQueryParam empQueryParam);
   /*
   新增员工
    */
    void save(Emp emp);

    /*
    批量删除员工
     */
    void delete(List<Integer> ids);

    /*
    根据id查询员工
     */
    Emp getInfo(Integer id);

    /*
    更新员工信息
     */
    void update(Emp emp);

    /*
    员工登录
     */
    LoginInfo login(Emp emp);

 List<Emp> find(Integer empId,String name);
}
