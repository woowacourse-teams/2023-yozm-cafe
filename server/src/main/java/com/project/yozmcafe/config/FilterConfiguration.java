package com.project.yozmcafe.config;

import com.project.yozmcafe.controller.filter.LatencyLoggingFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private static final int FIRST = 0;

    private final LatencyLoggingFilter latencyLogging;

    public FilterConfiguration(final LatencyLoggingFilter latencyLogging) {
        this.latencyLogging = latencyLogging;
    }

    @Bean
    public FilterRegistrationBean<LatencyLoggingFilter> latencyLoggingFilterFilter() {
        return registerFilter(latencyLogging, FIRST);
    }

    private <F extends Filter> FilterRegistrationBean<F> registerFilter(final F f, final int order) {
        final FilterRegistrationBean<F> filter = new FilterRegistrationBean<>(f);
        filter.setOrder(order);

        return filter;
    }
}
