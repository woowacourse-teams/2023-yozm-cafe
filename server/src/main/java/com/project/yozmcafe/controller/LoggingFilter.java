package com.project.yozmcafe.controller;

import com.project.yozmcafe.service.auth.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String KEY = "user";
    private static final String ANONYMOUS = "anonymous";

    private static final Logger logger = LoggerFactory.getLogger("API Request");

    private final JwtTokenProvider jwtTokenProvider;

    public LoggingFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        MDC.put(KEY, getMemberUUIDFromJWT(request));
        logger.info("Request For '{}'", request.getRequestURI());
        MDC.clear();

        filterChain.doFilter(request, response);
    }

    private String getMemberUUIDFromJWT(final HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(authorization)) {
            return ANONYMOUS;
        }

        final String token = authorization.replace("Bearer ", "");
        final String memberId = jwtTokenProvider.getMemberId(token);
        return UUID.nameUUIDFromBytes(memberId.getBytes()).toString();
    }
}
