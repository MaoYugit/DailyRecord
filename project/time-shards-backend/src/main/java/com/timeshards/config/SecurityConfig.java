package com.timeshards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用 CSRF (前后端分离不需要，因为我们不用 Session)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. 【关键】开启 CORS 支持 (让它去读取 WebConfig 里的配置)
                .cors(Customizer.withDefaults())

                // 3. 【关键】设置为无状态 (Stateless) —— 这一点非常重要！
                // 意味着后端不存 Session，每次请求都要带 Token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. 路径权限控制
                .authorizeHttpRequests(auth -> auth
                        // 放行静态资源和文档
                        .requestMatchers("/doc.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // 放行登录注册接口 (还没写，先预留)
                        .requestMatchers("/api/auth/**").permitAll()
                        // 放行公共 GET 接口 (比如看文章列表不需要登录)
                        // .requestMatchers(HttpMethod.GET, "/api/articles/**").permitAll()

                        // 其他接口默认全放行 (开发阶段)，等你也写了 JWT 过滤器再改成 .authenticated()
                        .anyRequest().permitAll()
                );

        // TODO: 这里以后要 addFilterBefore 加一个 JwtAuthenticationFilter

        return http.build();
    }

    // 密码加密器 (保持不变)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 【关键升级】暴露 AuthenticationManager Bean
     * 以后在 LoginService 里要用它来验证用户名密码
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}