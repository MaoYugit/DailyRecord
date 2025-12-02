package com.timeshards.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    // 密钥 (实际生产中应放在配置文件或环境变量中)
    private static final String SECRET = "TimeShardsSecretKeyTimeShardsSecretKeyTimeShardsSecretKey";

    // 过期时间: 7天 (毫秒)
    private static final long EXPIRATION = 604800000L;

    // 0.12.x 最佳实践：使用 SecretKey 类型，并指定 UTF-8 编码
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成 Token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims) // 0.12.x 使用 claims() 方法
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                // 签名设置：key 在前，算法可选（通常会自动根据 key 长度匹配 HS256/HS512）
                // 这里显式指定使用 HS256
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 从 Token 中获取用户名
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * 验证 Token 是否有效
     */
    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            // 解析失败（如签名不匹配、格式错误）默认为无效
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * 解析 Token 获取所有声明 (Claims)
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key) // 0.12.x 新语法：使用 verifyWith 传入 SecretKey
                .build()
                .parseSignedClaims(token) // 0.12.x 新语法：parseSignedClaims
                .getPayload(); // 0.12.x 新语法：getPayload (原 getBody)
    }
}