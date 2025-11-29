package com.timeshards.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 1. 允许的来源：生产环境请替换为具体的域名，如 "http://www.timeshards.com"
                // 使用 allowedOriginPatterns 比 allowedOrigins 更灵活，且支持 allowCredentials
                .allowedOriginPatterns("*")
                // 2. 允许的方法：通常这几个就够了
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 3. 允许的头信息
                .allowedHeaders("*")
                // 4. 是否允许携带 Cookie/凭证 (前后端分离必须开启)
                .allowCredentials(true)
                // 5. 【关键升级】预检请求缓存时间 (1小时)，减少浏览器发送 OPTIONS 请求的次数，提升性能
                .maxAge(3600);
    }
}