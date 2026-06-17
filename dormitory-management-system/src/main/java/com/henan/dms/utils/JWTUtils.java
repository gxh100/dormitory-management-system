package com.henan.dms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JWTUtils {
    private static final long TIME = 30 * 60 * 1000; // 30分钟过期
    private static final String SECRET = "$%1321FSF";  // 密钥，请与前端保持一致

    public static String getToken(Integer userId, String userName) {
        JwtBuilder builder = Jwts.builder();
        return builder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", userName)
                .claim("role", "admin")
                .claim("userid", userId)
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Claims parseToken(String token) {
        JwtParser parser = Jwts.parser();
        return parser.setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}