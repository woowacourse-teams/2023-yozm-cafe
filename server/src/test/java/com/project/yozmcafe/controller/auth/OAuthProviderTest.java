package com.project.yozmcafe.controller.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class OAuthProviderTest {

    @Test
    @DisplayName("OAuthProvider와 OAuthClient가 매핑되지 않으면 실패")
    void setUp() {
        assertDoesNotThrow(() -> OAuthProvider.GOOGLE);
    }
}
