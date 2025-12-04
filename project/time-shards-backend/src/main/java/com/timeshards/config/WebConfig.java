package com.timeshards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. 注入配置文件中的路径 (C:/Users/28745/Desktop/DailyRecord/project/)
    @Value("${upload.path}")
    private String uploadPath;

    // 2. 跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 3. 静态资源映射 (把 URL 指向本地硬盘)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当访问 /uploads/xxxxx.jpg 时
        registry.addResourceHandler("/uploads/**")
                // 去本地磁盘的 uploadPath 目录下找
                // 注意：必须加 "file:" 前缀，告诉 Spring 这是一个文件系统路径
                .addResourceLocations("file:" + uploadPath);
    }
}