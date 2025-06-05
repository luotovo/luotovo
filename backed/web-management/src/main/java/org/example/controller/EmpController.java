package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*
    员工管理的controller
    
 */
@RequestMapping("/emps")
@Slf4j
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询:{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);

    }
    /*
    新增员工
     */
    @PostMapping()
    public Result save(@RequestBody Emp emp){
        log.info("新增员工:{}",emp);
        empService.save(emp);
        return Result.success();
    }
    /*
    删除员工  []
     */

//    @DeleteMapping
//    public Result delete(Integer[] ids){
//        log.info("删除员工:{}", Arrays.toString(ids));
//        return Result.success();
//
//    }
    /*
    删除员工  List
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工:{}",ids);
        empService.delete(ids);
        return Result.success();

    }
    /*
    根据id查询员工
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工:{}",id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);

    }
    /*
    修改员工信息或工作经历
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工:{}",emp);
        empService.update(emp);
        return Result.success();
    }
    //查询员工的姓名和id
    @GetMapping("/list")
    public Result find(@PathVariable Integer empId, @PathVariable String name){
        log.info("查询员工姓名和id");
        List<Emp> empList = empService.find(empId, name);
        return Result.success(empList);
    }


}































