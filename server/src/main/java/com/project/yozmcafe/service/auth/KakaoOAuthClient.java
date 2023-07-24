package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.domain.auth.token.KakaoToken;
import com.project.yozmcafe.domain.auth.token.OAuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthClient extends OAuthClient {

    @Value("${spring.auth.kakao.tokenUri}")
    private String kakaoTokenUri;
    @Value("${spring.auth.kakao.clientId}")
    private String clientId;
    @Value("${spring.auth.kakao.clientSecret}")
    private String clientSecret;
    @Value("${spring.auth.kakao.redirectUri}")
    private String redirectUri;
    @Value("${spring.auth.kakao.authUri}")
    private String kakaoAuthUri;
    @Value("${spring.auth.kakao.authRedirectUri}")
    private String authRedirectUri;
    @Value("${spring.auth.kakao.scope}")
    private String scope;

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
    protected String tokenUri() {
        return kakaoTokenUri;
    }

    @Override
    public String authUri() {
        return kakaoAuthUri;
    }

    @Override
    protected String authRedirectUri() {
        return authRedirectUri;
    }

    @Override
    protected String scope() {
        return scope;
    }
}
