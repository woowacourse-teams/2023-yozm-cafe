package com.project.yozmcafe.config;

import com.project.yozmcafe.controller.LoginArgumentResolver;
import com.project.yozmcafe.controller.StringToOAuthProviderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final LoginArgumentResolver loginArgumentResolver;
    private final StringToOAuthProviderConverter stringToOAuthProviderConverter;

    public WebConfiguration(final LoginArgumentResolver loginArgumentResolver,
                            final StringToOAuthProviderConverter stringToOAuthProviderConverter) {
        this.loginArgumentResolver = loginArgumentResolver;
        this.stringToOAuthProviderConverter = stringToOAuthProviderConverter;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(stringToOAuthProviderConverter);
    }
}
