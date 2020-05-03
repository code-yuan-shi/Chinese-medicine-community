package com.bbgu.zmz.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.*;

import java.util.Properties;



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
                        "/upload/**",
                        "/ad/**",
                        "/api/top",
                        "/api/check",
                        "/callback",
                        "/logout",
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
                        "/user/home",
                        "/user/forget",
                        "/user/reset",
                        "/user/repass",
                        "/user/resend",
                        "/user/activate",
                        "/collection/find"
                        );
}
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                Properties props=System.getProperties(); //获得系统属性集
                String osName = props.getProperty("os.name"); //操作系统名称
                if(osName.indexOf("Win") != -1){
                    registry.addResourceHandler("/upload/**").addResourceLocations("file:D://upload/");
                    registry.addResourceHandler("/ad/**").addResourceLocations("file:D://ad/");
                }else{
                    registry.addResourceHandler("/upload/**").addResourceLocations("file:/data/wwwroot/upload/");
                    registry.addResourceHandler("/ad/**").addResourceLocations("file:/data/wwwroot/ad/");
                }
                registry.addResourceHandler("swagger-ui.html")
                        .addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
          }

/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }*/


}
