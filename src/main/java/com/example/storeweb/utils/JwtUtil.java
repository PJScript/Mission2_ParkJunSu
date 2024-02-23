package com.example.storeweb.utils;

import com.example.storeweb.auth.dto.TenantDto;
import com.example.storeweb.auth.entity.TenantEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * JWT 제어를 위한 유틸리티 클래스
 */
@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;


    /**
     * {jwt.secret} - application.yaml에 정의한 secret 입니다. <br />
     * {jwt.expire_time} - application.yaml에 정의한 토큰 만료시간 입니다.
     * */
    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expire_time}") long accessTokenExpTime
    ) {
        // 인코딩된 byte배열로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;

    }

    /**
     * Access Token 생성
     * @param tenant
     * @return Access Token String
     */
    public String createAccessToken(TenantDto tenant) {
        return createToken(tenant, accessTokenExpTime);
    }


    /**
     * JWT 생성
     * @param tenant
     * @param expireTime
     * @return JWT String
     */
    private String createToken(TenantDto tenant, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("uuid", tenant.getUuid());


        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Token에서  uuid 추출
     * @param token
     * @return User ID
     */
    public Long getUserId(String token) {
        return parseClaims(token).get("uuid", Long.class);
    }


    /**
     * JWT 검증
     * @param token
     * @return IsValidate
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}