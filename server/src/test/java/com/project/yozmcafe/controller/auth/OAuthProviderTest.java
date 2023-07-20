package com.project.yozmcafe.controller.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class OAuthProviderTest {

    @Test
    @DisplayName("OAuthProvider와 OAuthClient가 매핑되지 않으면 실패")
    void setUp() {
        assertDoesNotThrow(() -> OAuthProvider.GOOGLE);
    }

    @Test
    @DisplayName("Authorization Uri를 받아올 수 있다.")
    void getAuthorizationUrl() {
        final OAuthProvider provider = OAuthProvider.from("kakao");
        assertThat(provider.getAuthorizationUrl()).contains("response_type", "redirect_uri", "client_id", "scope");
    }
}
