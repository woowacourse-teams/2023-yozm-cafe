package com.project.yozmcafe.controller.auth;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_OAUTH_CLIENT;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_OAUTH_PROVIDER;

import java.util.Objects;

import org.springframework.context.annotation.Configuration;

import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.InternalServerException;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;
import com.project.yozmcafe.service.auth.OAuthClient;

import jakarta.annotation.PostConstruct;

public enum OAuthProvider {
    GOOGLE,
    KAKAO;

    private OAuthClient oAuthClient;

    public static OAuthProvider from(final String providerName) {
        try {
            return OAuthProvider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(NOT_EXISTED_OAUTH_PROVIDER);
        }
    }

    private void setOAuthClient(final OAuthClient oAuthClient) {
        this.oAuthClient = oAuthClient;
    }

    public MemberInfo getUserInfo(final String code) {
        return oAuthClient.getUserInfo(code);
    }

    public String getAuthorizationUrl() {
        return oAuthClient.getAuthorizationUrl();
    }

    public String getProviderName() {
        return name().toLowerCase();
    }

    @Configuration
    private static class DependencyInjector {
        private final GoogleOAuthClient googleOAuthClient;
        private final KakaoOAuthClient kakaoOAuthClient;

        public DependencyInjector(final GoogleOAuthClient googleOAuthClient, final KakaoOAuthClient kakaoOAuthClient) {
            this.googleOAuthClient = googleOAuthClient;
            this.kakaoOAuthClient = kakaoOAuthClient;
        }

        @PostConstruct
        private void setUp() {
            for (OAuthProvider oAuthProvider : OAuthProvider.values()) {
                if (oAuthProvider.equals(OAuthProvider.GOOGLE)) {
                    oAuthProvider.setOAuthClient(googleOAuthClient);
                }
                if (oAuthProvider.equals(OAuthProvider.KAKAO)) {
                    oAuthProvider.setOAuthClient(kakaoOAuthClient);
                }
                if (Objects.isNull(oAuthProvider.oAuthClient)) {
                    throw new InternalServerException(NOT_EXISTED_OAUTH_CLIENT);
                }
            }
        }
    }
}
