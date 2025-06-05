package org.example.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 密钥，与测试类中的一致
    private static final String SECRET_KEY = "bHVvdG92bw==";

    // 过期时间12小时（毫秒）
    private static final long EXPIRATION_TIME = 12 * 3600 * 1000;

    /**
     * 生成JWT令牌
     * @param claims 自定义claims数据
     * @return 生成的JWT令牌
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims) // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间12小时
                .compact(); // 生成jwt
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌
     * @return Claims对象，包含解析出的数据
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置密钥
                .parseClaimsJws(token) // 解析JWT
                .getBody(); // 获取自定义claims
    }
}
