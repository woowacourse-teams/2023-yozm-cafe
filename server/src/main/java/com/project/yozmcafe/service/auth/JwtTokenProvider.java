package com.project.yozmcafe.service.auth;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_TOKEN;
import static com.project.yozmcafe.exception.ErrorCode.TOKEN_IS_EXPIRED;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.yozmcafe.exception.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

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

    public String createAccessFrom(final String memberId) {
        return builder(accessTokenExpired)
                .setSubject(memberId)
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
            throw new TokenException(INVALID_TOKEN);
        }
    }

    public String refreshAccessToken(final String access, final String refresh) {
        validate(refresh);
        return createAccessFrom(getMemberId(access));
    }

    public void validate(final String token) {
        final Claims claims = toClaims(token);

        if (claims.getExpiration().before(new Date())) {
            throw new TokenException(TOKEN_IS_EXPIRED);
        }
    }
}
