package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * 全局异常处理器
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handleException( Exception e){
        log.error("程序出错了",e);
        return Result.error("出错了，请联系管理员");
    }

    public Result handleDuplicateException(DuplicateKeyException e) {
        //优先抓取子类的异常，避免父类异常被其他子类异常覆盖
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String []arr =  errMsg.split(" ");
        return Result.error("【"+arr[2]+"】已存在");
    }
}

