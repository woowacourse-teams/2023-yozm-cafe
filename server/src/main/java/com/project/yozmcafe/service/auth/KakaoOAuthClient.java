package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.domain.auth.token.KakaoToken;
import com.project.yozmcafe.domain.auth.token.OAuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthClient extends OAuthClient {

    @Value("${spring.auth.kakao.uri}")
    private String kakaoUri;
    @Value("${spring.auth.kakao.clientId}")
    private String clientId;
    @Value("${spring.auth.kakao.clientSecret}")
    private String clientSecret;
    @Value("${spring.auth.kakao.redirectUri}")
    private String redirectUri;

    @Override
    protected Class<? extends OAuthToken> getType() {
        return KakaoToken.class;
    }

    @Override
    protected String clientId() {
        return clientId;
    }

    @Override
    protected String clientSecret() {
        return clientSecret;
    }

    @Override
    protected String redirectUri() {
        return redirectUri;
    }

    @Override
    protected String requestUri() {
        return kakaoUri;
    }
}
