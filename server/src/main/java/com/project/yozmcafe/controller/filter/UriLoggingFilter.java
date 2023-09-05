package com.project.yozmcafe.controller.filter;

import com.project.yozmcafe.service.auth.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class UriLoggingFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String KEY = "user";
    private static final String ANONYMOUS = "anonymous";
    private static final String BEARER = "Bearer ";

    private static final Logger LOGGER = LoggerFactory.getLogger(UriLoggingFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    public UriLoggingFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        MDC.put(KEY, getMemberIdFromJwt(request));
        LOGGER.info("Request For '{}'", request.getRequestURI());
        MDC.clear();

        filterChain.doFilter(request, response);
    }

    private String getMemberIdFromJwt(final HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(authorization)) {
            return ANONYMOUS;
        }

        final String token = authorization.replace(BEARER, "");
        return jwtTokenProvider.getMemberId(token);
    }
}
