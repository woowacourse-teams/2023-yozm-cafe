package com.project.yozmcafe.domain.auth.token;

import com.project.yozmcafe.controller.auth.MemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OAuthTokenTest {

    @Test
    @DisplayName("구글 토큰정보에서 UserInfo를 파싱한다.")
    void toUserInfo() {
        //given
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJvY2VhbklkIiwibmFtZSI6Im9jZWFuIiwicGljdHVyZSI6Im9jZWFuUGljdHVyZSIsImlhdCI6MTAwMDAwMDB9.xsaiWSGzjPYNrC5t7ykblz5GpGZMkwwKvQKjY-twE9o";
        final GoogleToken googleToken = new GoogleToken(token);

        //when
        final MemberInfo memberInfo = googleToken.toUserInfo();

        //then
        assertAll(
                () -> assertThat(memberInfo.name()).isEqualTo("ocean"),
                () -> assertThat(memberInfo.openId()).isEqualTo("oceanId"),
                () -> assertThat(memberInfo.image()).isEqualTo("oceanPicture")
        );
    }
}
