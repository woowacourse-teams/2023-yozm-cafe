package com.project.yozmcafe.config;

import com.project.yozmcafe.controller.filter.LatencyLoggingFilter;
import com.project.yozmcafe.controller.filter.UriLoggingFilter;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private static final int FIRST = 0;
    private static final int SECOND = 1;

    private final JwtTokenProvider jwtTokenProvider;

    public FilterConfiguration(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public FilterRegistrationBean<LatencyLoggingFilter> latencyLoggingFilterFilter() {
        final LatencyLoggingFilter latencyLogging = new LatencyLoggingFilter();
        return registerFilter(latencyLogging, FIRST);
    }

    @Bean
    public FilterRegistrationBean<UriLoggingFilter> uriLoggingFilterFilterRegistrationBean() {
        final UriLoggingFilter uriLogging = new UriLoggingFilter(jwtTokenProvider);
        return registerFilter(uriLogging, SECOND);
    }

    private <F extends Filter> FilterRegistrationBean<F> registerFilter(final F f, final int order) {
        final FilterRegistrationBean<F> filter = new FilterRegistrationBean<>(f);
        filter.setOrder(order);

        return filter;
    }
}
