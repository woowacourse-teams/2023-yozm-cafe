package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.domain.auth.token.GoogleToken;
import com.project.yozmcafe.domain.auth.token.OAuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthClient extends OAuthClient {

    @Value("${spring.auth.google.tokenUri}")
    private String googleTokenUri;
    @Value("${spring.auth.google.clientId}")
    private String clientId;
    @Value("${spring.auth.google.clientSecret}")
    private String clientSecret;
    @Value("${spring.auth.google.redirectUri}")
    private String redirectUri;
    @Value("${spring.auth.google.authUri}")
    private String googleAuthUri;
    @Value("${spring.auth.google.authRedirectUri}")
    private String authRedirectUri;
    @Value("${spring.auth.google.scope}")
    private String scope;

    @Override
    protected Class<? extends OAuthToken> getType() {
        return GoogleToken.class;
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
        return googleTokenUri;
    }

    @Override
    protected String authUri() {
        return googleAuthUri;
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
