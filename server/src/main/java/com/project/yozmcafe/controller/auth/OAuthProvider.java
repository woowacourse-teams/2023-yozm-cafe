package com.project.yozmcafe.controller.auth;

import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import com.project.yozmcafe.service.auth.OAuthClient;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

public enum OAuthProvider {
    GOOGLE;

    private OAuthClient oAuthClient = null;

    public static OAuthProvider from(final String providerName) {
        try {
            return OAuthProvider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 OAuth 로그인입니다.");
        }
    }

    public void setoAuthClient(final OAuthClient oAuthClient) {
        this.oAuthClient = oAuthClient;
    }

    public MemberInfo getUserInfo(final String code) {
        return oAuthClient.getUserInfo(code);
    }

    @Configuration
    private static class DependencyInjector {
        private final GoogleOAuthClient googleOAuthClient;

        public DependencyInjector(final GoogleOAuthClient googleOAuthClient) {
            this.googleOAuthClient = googleOAuthClient;
        }

        @PostConstruct
        private void setUp() {
            for (OAuthProvider oAuthProvider : OAuthProvider.values()) {
                if (oAuthProvider.equals(OAuthProvider.GOOGLE)) {
                    oAuthProvider.setoAuthClient(googleOAuthClient);
                }

                if (Objects.isNull(oAuthProvider.oAuthClient)) {
                    throw new IllegalStateException("OAuthProvider에게 맞는 OAuthClient가 존재하지 않습니다.");
                }
            }
        }
    }
}
