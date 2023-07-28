package com.project.yozmcafe.controller.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @ParameterizedTest(name = "{0} providerName을 소문자로 반환한다.")
    @MethodSource("provideOAuthProviderAndName")
    @DisplayName("providerName을 소문자로 반환한다.")
    void getProviderName(OAuthProvider oAuthProvider, String expected) {
        assertThat(oAuthProvider.getProviderName()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideOAuthProviderAndName() {
        return Stream.of(
                Arguments.of(OAuthProvider.KAKAO, "kakao"),
                Arguments.of(OAuthProvider.GOOGLE, "google")
        );
    }
}
