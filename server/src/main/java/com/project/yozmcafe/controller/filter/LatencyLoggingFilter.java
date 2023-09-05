package com.project.yozmcafe.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LatencyLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyLoggingFilter.class);

    private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        setStartTime();
        filterChain.doFilter(request, response);

        final long durationMillis = getDurationMillis();
        LOGGER.info("Latency: {}s", (double) durationMillis / 1000);
    }

    private void setStartTime() {
        threadLocal.set(System.currentTimeMillis());
    }

    private long getDurationMillis() {
        final long start = threadLocal.get();
        final long end = System.currentTimeMillis();

        threadLocal.remove();
        return end - start;
    }
}
