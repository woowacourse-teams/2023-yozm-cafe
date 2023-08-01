package com.project.yozmcafe.domain.auth.token;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_OAUTH_USER_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.exception.InternalServerException;

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

    @Test
    @DisplayName("Provider로 부터 받은 토큰의 payload에 subject, name, image 중 하나라도 존재하지 않으면 예외가 발생한다.")
    void parseFail() {
        //given
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaW1hZ2UiOiJpbWFnZSIsImlhdCI6MTUxNjIzOTAyMn0.SlwtSp0oOJpBToHUNul58kOEhbU7EfcFN3hESoo-DU4";
        final GoogleToken googleToken = new GoogleToken(token);

        //when & then
        assertThatThrownBy(googleToken::toUserInfo)
                .isInstanceOf(InternalServerException.class)
                .hasMessage(INVALID_OAUTH_USER_INFO.getMessage());


    }
}
