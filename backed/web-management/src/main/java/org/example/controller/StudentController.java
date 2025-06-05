package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.pojo.Student;
import org.example.pojo.StudentParam;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    /*
    * 查询全部的部门数据/分页
     */
    @GetMapping
    public Result page(StudentParam studentParam){
        log.info("查询全部的同学数据");
        PageResult<Student> pageResult = studentService.page(studentParam);
        return Result.success(pageResult);

    }
    /*
    删除学员
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("删除学员:{}",ids);
        studentService.deleteById(ids);
        return Result.success();
    }
    /*
    添加学员
     */
    @PostMapping
    public Result save(@RequestBody Student student){
        log.info("添加员工:{}",student);
        studentService.save(student);
        return Result.success();
    }
    /*
     根据id查询员工
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询员工:{}",id);
        Student student = studentService.getInfo(id);
        return Result.success(student);
    }
    /*
      修改学员
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改员工:{}",student);
        studentService.update(student);
        return Result.success();
    }
    /*
    违纪处理
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violationHandle(@PathVariable Integer id,@PathVariable Short score){
        if(score == null ){
            score = 0;
        }
        log.info("违纪处理:{},{}",id, score);
        studentService.violationHandleById(id, score);
        return Result.success();
    }

}
