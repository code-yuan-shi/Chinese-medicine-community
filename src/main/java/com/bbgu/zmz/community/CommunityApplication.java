package com.bbgu.zmz.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.bbgu.zmz.community.mapper")
public class CommunityApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CommunityApplication.class);
    }

}
