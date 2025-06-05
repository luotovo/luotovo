package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    // 用于班级列表数据的分页查询
    @Autowired
    private ClazzService clazzService;
    /*
    1.班级列表查询
     */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询:{}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService. page(clazzQueryParam);
        return Result.success(pageResult);

    }
    /*
    2.班级数据删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除班级:{}",id);
        clazzService.delete(id);
        return Result.success();
    }
    /*
    3.新增班级
     */
    @PostMapping
    public Result add(@RequestBody Clazz clazz){
        log.info("新增班级:{}",clazz);
        clazzService.add(clazz);
        return Result.success();
    }
    /*
    4.根据id查询班级的信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询班级:{}",id);
        Clazz clazz =clazzService.getInfo(id);
        return Result.success(clazz);

    }
    /*
    5.修改班级信息
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级:{}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }
    /*
    6.查询所有班级信息
     */
    @GetMapping("/list")
    public Result findAll(){
        log.info("查询所有班级信息");
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);

    }




}
