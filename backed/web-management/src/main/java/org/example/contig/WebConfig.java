package org.example.contig;

import org.example.interceptor.DemoInterceptor;
import org.example.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
 配置类  封装了component
 */

@Configuration
public class WebConfig implements WebMvcConfigurer{

//    @Autowired
//    private DemoInterceptor demoInterceptor;
//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(tokenInterceptor)
//            .addPathPatterns("/**")
//                .excludePathPatterns("/login");//拦截所有请求
////        registry.addInterceptor(demoInterceptor)
////                .addPathPatterns("/**")
////                .excludePathPatterns("/login");//拦截所有请求
//
//    }
}
