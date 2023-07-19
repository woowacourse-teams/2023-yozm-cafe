package com.project.yozmcafe.service.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String INVALID_TOKEN = "유효하지 않은 인증 정보입니다.";

    private final SecretKey key;
    private final long accessTokenExpired;
    private final long refreshTokenExpired;

    public JwtTokenProvider(@Value("${spring.auth.key}") final String key,
                            @Value("${spring.auth.accessTokenExpired}") final long accessTokenExpired,
                            @Value("${spring.auth.refreshTokenExpired}") final long refreshTokenExpired) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpired = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
    }

    public String createAccess(final String payload) {
        return builder(accessTokenExpired)
                .setSubject(payload)
                .compact();
    }

    private JwtBuilder builder(final long expired) {
        final Date validity = new Date(System.currentTimeMillis() + expired);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256);
    }

    public String createRefresh() {
        return builder(refreshTokenExpired).compact();
    }

    public String getMemberId(final String token) {
        return toClaims(token).getSubject();
    }

    private Claims toClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (final ExpiredJwtException expired) {
            return expired.getClaims();
        } catch (final Exception e) {
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
    }

    public String refreshAccessToken(final String access, final String refresh) {
        validate(refresh);
        return createAccess(getMemberId(access));
    }

    public void validate(final String token) {
        final Claims claims = toClaims(token);

        if (claims.getExpiration().before(new Date())) {
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
    }
}

