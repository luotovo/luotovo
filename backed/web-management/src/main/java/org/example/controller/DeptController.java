package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anno.Log;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

//    private static Logger  log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;
    //请求前缀固定 ，/depts

//    @RequestMapping(value ="/depts",method = GET )//METHOD 指定请求方式
    @GetMapping
    public Result list() {
//        System.out.println("查询全部的部门数据");
        log.info("查询全部的部门数据");
        List<Dept> deptList =deptService.findAll();
        return Result.success(deptList);
        //需要返回数据，调用有参方法
    }
    /*
    * 删除1.根据HttpServletRequest获取请求参数
     */
//    @DeleteMapping("depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int i = Integer.parseInt(idStr);
//        System.out.println("删除部门"+i);
//        return Result.success();
//        //不需要返回数据，调用无参方法
//    }

    /*
    * 删除2.根据请求参数获取请求参数@RequestParam
    * 注意事项：一旦声明了@RequestParam，那么参数在请求时必须传递，不然会报错（400）
    * required = false 表示参数可以不传递,默认为true
     */
//    @DeleteMapping("depts")
//    public Result delete(@RequestParam(value = "id", required = false) Integer id){
//        System.out.println("删除部门"+ id);
//        return Result.success();
//        //不需要返回数据，调用无参方法
//    }

    /*
     方式三：省略 @RequestParam （前端传递的请求参数名与服务端方法形参名一致）
    */
//    @Log
    @DeleteMapping
    public Result delete(Integer id){
//        System.out.println("删除部门"+ id);
        log.info("删除部门:{ }",id);
        deptService.deleteById(id);
        return Result.success();
        //不需要返回数据，调用无参方法
    }
    /*
    添加部门
     */
//    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
//        System.out.println("添加部门:"+ dept);
        log.info("添加部门:{ }",dept);
        deptService.add(dept);
        return Result.success();
    }
    /*
    查询部门
     */

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
//        System.out.println("查询部门id为：" + id);
        log.info("查询部门id为：{ }",id);
        Dept dept = deptService.findById(id);//封装对象
        return Result.success(dept);
    }

    /*
     * 根据id  修改部门 和 updateTime
     */
//    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
//        System.out.println("修改部门：" + dept);
        log.info("修改部门:{ }",dept);
        deptService.update(dept);
        return Result.success();
    }
}


