package com.project.yozmcafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageConfiguration {
    private static void customize(PageableHandlerMethodArgumentResolver page) {
        page.setOneIndexedParameters(true);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return PageConfiguration::customize;
    }
}
