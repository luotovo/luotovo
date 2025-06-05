package org.example.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.Utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        //1. 获取请求路径
//        String path = request.getRequestURI();///employee/login uri
//
//        //2.是否是登录请求，/login 是登录则放行，
//        if(path.contains("/login")){
//            log.info("登录请求，放行");
//            return true;
//        }

        //3.获取请求头的token
        String token = request.getHeader("token");

        //4.判断token是否存在，不存在说明没有登录，返回401
        if(token == null || token.isEmpty()){
            log.info("令牌未空，相应401");
            response.setStatus(401);
            return false;

        }
        //5.令牌存在，则校验令牌，验证失败返回401
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌非法，返回401");
            response.setStatus(401);
            return false;
        }

        //6.验证成功，放行

        log.info("令牌合法，放行");
        return true;
    }

}
