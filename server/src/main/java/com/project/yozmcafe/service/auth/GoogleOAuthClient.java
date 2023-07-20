package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.domain.auth.token.GoogleToken;
import com.project.yozmcafe.domain.auth.token.OAuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthClient extends OAuthClient {

    @Value("${spring.auth.google.uri}")
    private String googleUri;
    @Value("${spring.auth.google.clientId}")
    private String clientId;
    @Value("${spring.auth.google.clientSecret}")
    private String clientSecret;
    @Value("${spring.auth.google.redirectUri}")
    private String redirectUri;

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
    protected String requestUri() {
        return googleUri;
    }
}
