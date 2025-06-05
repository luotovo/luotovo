package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Emp;
import org.example.pojo.LoginInfo;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/*
 登录controller
 */
@Slf4j
@RestController

public class LoginController {

    @Autowired
    private EmpService empService;

    /*
    登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("登录请求:{}",emp);
        LoginInfo Info = empService.login(emp);
        if(Info!=null){
            return Result.success(Info);
        }
        return Result.error("用户名或密码错误");

    }
}
