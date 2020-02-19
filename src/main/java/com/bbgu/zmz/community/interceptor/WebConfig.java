package com.bbgu.zmz.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**")
                .excludePathPatterns(
                        "/static/**",
                        "/api/top",
                        "/api/check",
                        "/callback",
                        "/logout",
                        "/category/**",
                        "/search",
                        "/jie/detail/*",
                        "/message/nums",
                        "/sign/status",
                        "/sign/getSign",
                        "/user/login",
                        "/user/reg",
                        "/user/forget",
                        "/user/checkuser",
                        "/user/checkemail",
                        "/user/activemail/*",
                        "/user/doreg",
                        "/user/dologin",
                        "/user/home/*",
                        "/user/forget",
                        "/user/reset",
                        "/user/repass",
                        "/"
                        );
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
          }
}
