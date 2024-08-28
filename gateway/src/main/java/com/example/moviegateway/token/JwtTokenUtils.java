package com.example.moviegateway.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @Author Liwei
 * @Date 2021-08-13 11:24
 */
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private static final String USERNAME = Claims.SUBJECT;
    /**
     * 创建时间
     */
    private static final String CREATED = "created";
    /**
     * 密钥
     */
    private static final String SECRET = "abcdefgh";
    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成令牌
     *
     * @param username
     * @return 令牌
     */
    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(USERNAME, username);
        claims.put(CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            // 判断异常对象是否是token过期异常
            if (e instanceof ExpiredJwtException) {
                username = ((ExpiredJwtException) e).getClaims().getSubject();
            }
        }
        return username;
    }


    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e = (ExpiredJwtException) e;
            claims = ((ExpiredJwtException) e).getClaims();
        } finally {
            return claims;
        }
    }

    /**
     * 验证令牌
     *
     * @param token
     * @return
     */
    public static Boolean validateToken(String token) {
        String userName = getUsernameFromToken(token);
        boolean result = (!"".equals(userName) && userName != null && !isTokenExpired(token));
        return result;
    }

    /**
     * 刷新令牌（token过期，重新颁发
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            boolean result = expiration.before(new Date());
            return result;
        } catch (Exception e) {
            return false;
        }
    }

}