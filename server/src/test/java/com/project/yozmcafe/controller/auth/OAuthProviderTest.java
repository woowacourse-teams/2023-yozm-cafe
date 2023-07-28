package com.project.yozmcafe.controller.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class OAuthProviderTest {

    @Test
    @DisplayName("OAuthProvider와 OAuthClient가 매핑되지 않으면 실패")
    void setUp() {
        assertDoesNotThrow(() -> OAuthProvider.GOOGLE);
    }

    @ParameterizedTest(name = "{0} 인증 Uri를 받아올 수 있다.")
    @EnumSource(value = OAuthProvider.class)
    @DisplayName("인증 Uri 를 받아올 수 있다.")
    void getAuthorizationUrl(OAuthProvider oAuthProvider) {
        assertThat(oAuthProvider.getAuthorizationUrl()).contains("response_type", "redirect_uri", "client_id", "scope");
    }
}
