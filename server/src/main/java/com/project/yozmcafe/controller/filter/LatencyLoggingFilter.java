package com.project.yozmcafe.controller.filter;

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

@Component
public class LatencyLoggingFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String KEY = "user";
    private static final String ANONYMOUS = "anonymous";
    private static final String BEARER = "Bearer ";

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyLoggingFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final LatencyRecorder latencyRecorder;
    private final QueryInspector queryInspector;

    public LatencyLoggingFilter(final JwtTokenProvider jwtTokenProvider, final LatencyRecorder latencyRecorder,
                                final QueryInspector queryInspector) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.latencyRecorder = latencyRecorder;
        this.queryInspector = queryInspector;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        MDC.put(KEY, getMemberIdFromJwt(request));
        latencyRecorder.start();

        filterChain.doFilter(request, response);

        final double durationSeconds = latencyRecorder.getDurationSeconds();
        final int queryCount = queryInspector.getQueryCount();

        LOGGER.info("Latency: {}s, Query Count: {}, Request URI: {}", durationSeconds, queryCount, request.getRequestURI());
        MDC.clear();
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
