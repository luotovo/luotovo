package org.example.Aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.Utils.CurrentHolder;
import org.example.mapper.OperateLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.pojo.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 定义切点：拦截controller包下的所有方法


    @Around("@annotation(org.example.anno.Log)")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 记录结束时间
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        // 获取方法签名


        // 获取请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取操作人ID（这里假设从请求头或Session中获取，根据实际情况调整）


        // 构建操作日志对象
        OperateLog Log = new OperateLog();
        Log.setOperateEmpId(getCurrentId());
        Log.setOperateTime(LocalDateTime.now());
        Log.setClassName(joinPoint.getTarget().getClass().getName());
        Log.setMethodName(joinPoint.getSignature().getName());
        Log.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        Log.setReturnValue(result != null ? result.toString() : "void");
        Log.setCostTime(costTime);

        // 保存操作日志

        log.info("记录操作日志：{}", log);
        operateLogMapper.insert(Log);

        return result;
    }
    private Integer getCurrentId() {
        return CurrentHolder.getCurrentId();
    }
}
