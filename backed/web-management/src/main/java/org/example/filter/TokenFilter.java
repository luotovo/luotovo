package org.example.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.Utils.CurrentHolder;
import org.example.Utils.JwtUtils;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse  resp = (HttpServletResponse) servletResponse;

         //1. 获取请求路径
        String path = req.getRequestURI();///employee/login uri

        //2.是否是登录请求，/login 是登录则放行，
        if(path.contains("/login")){
            log.info("登录请求，放行");
            filterChain.doFilter(req,resp);
            return;
        }

        //3.获取请求头的token
        String token = req.getHeader("token");

        //4.判断token是否存在，不存在说明没有登录，返回401
        if(token == null || token.isEmpty()){
            log.info("令牌未空，相应401");
            resp.setStatus(401);
            return;

        }
        //5.令牌存在，则校验令牌，验证失败返回401
        try {
            Claims claims = JwtUtils.parseToken(token);
            //获取员工id
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("当前员工id为：{},将其存入   ThreadLocal",empId);

        } catch (Exception e) {
            log.info("令牌非法，返回401");
            resp.setStatus(401);
            return;
        }

        //6.验证成功，放行

        log.info("令牌合法，放行");
        filterChain.doFilter(req,resp);

        //7.删除线程变量的数据
        CurrentHolder.remove();
    }
}
