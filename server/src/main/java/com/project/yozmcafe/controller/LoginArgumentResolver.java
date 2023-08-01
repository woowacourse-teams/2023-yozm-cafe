package com.project.yozmcafe.controller;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_TOKEN;
import static com.project.yozmcafe.exception.ErrorCode.TOKEN_NOT_EXIST;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.TokenException;
import com.project.yozmcafe.service.auth.JwtTokenProvider;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public LoginArgumentResolver(final JwtTokenProvider jwtTokenProvider, final MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String token = parseTokenFrom(webRequest);

        jwtTokenProvider.validate(token);
        return memberRepository.findById(jwtTokenProvider.getMemberId(token))
                .orElseThrow(() -> new TokenException(INVALID_TOKEN));
    }

    private String parseTokenFrom(final NativeWebRequest webRequest) {
        String value = webRequest.getHeader(AUTHORIZATION);
        if (value == null) {
            throw new TokenException(TOKEN_NOT_EXIST);
        }

        return value.replace(BEARER, "");
    }

}
