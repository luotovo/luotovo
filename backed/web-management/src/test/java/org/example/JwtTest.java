package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGenerateJwt() {
        Map<String, Object> dataMap= new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "bHVvdG92bw==")
                .addClaims(dataMap)//添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))//设置过期时间
                .compact();//生成jwt
        System.out.println(jwt);
    }
    /*
    解析JWT
     */
    @Test
    public void testParseJwt() {
        String token ="eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc0NzQ2NTE5NX0.vn2W3IKlTB1Aq4ry-B__f9MNnYZTjAU50HY53QqBkjM";
        Claims claims = Jwts.parser().setSigningKey("bHVvdG92bw==")//设置密钥
                .parseClaimsJws(token)//解析JWT
                .getBody();//获取自定义
        System.out.println(claims);
    }
}
