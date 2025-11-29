package com.timeshards.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 定义 SecurityScheme (告诉文档我们要用 JWT)
        String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                // 1. 设置文档基本信息
                .info(new Info()
                        .title("Time Shards 后端接口文档")
                        .version("v1.0.0")
                        .description("基于 Spring Boot 3 + Knife4j 的博客后端系统")
                        .contact(new Contact().name("MaoYu").email("2874553847@qq.com").url("https://github.com/MaoYugit"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))

                // 2. 【关键升级】添加全局鉴权按钮
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}