package com.project.yozmcafe.controller;

import com.project.yozmcafe.exception.TokenException;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.project.yozmcafe.exception.ErrorCode.TOKEN_NOT_EXIST;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public LoginArgumentResolver(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public String resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String token = parseTokenFrom(webRequest);

        jwtTokenProvider.validate(token);
        return jwtTokenProvider.getMemberId(token);
    }

    private String parseTokenFrom(final NativeWebRequest webRequest) {
        String value = webRequest.getHeader(AUTHORIZATION);
        if (value == null) {
            throw new TokenException(TOKEN_NOT_EXIST);
        }

        return value.replace(BEARER, "");
    }
}
