package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.controller.dto.AuthorizationUrlDto;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.Mockito.doReturn;

@Transactional
class AuthServiceTest extends BaseTest {

    @SpyBean
    private GoogleOAuthClient googleOAuthClient;
    @SpyBean
    private KakaoOAuthClient kakaoOAuthClient;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("회원가입된 상태의 유저로 createAccessToken 호출하면 멤버를 새로 저장하지 않는다")
    void createAccessToken() {
        //given
        doReturn(new MemberInfo("1234", "", ""))
                .when(googleOAuthClient).getUserInfo(anyString());
        memberRepository.save(new Member("1234", "연어", "image"));

        //when
        authService.createAccessToken("135", OAuthProvider.GOOGLE);

        //then
        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("회원가입된 상태의 유저가 아닐 때 createAccessToken 호출하면 멤버를 새로 저장하고, 모든 카페를 해당 멤버의 unViewedCafe 로 같이 저장한다. ")
    void createAccessToken2() {
        //given
        String memberId = "1234";
        doReturn(new MemberInfo(memberId, "", ""))
                .when(googleOAuthClient).getUserInfo(anyString());
        saveCafes();

        //when
        authService.createAccessToken("135", OAuthProvider.GOOGLE);

        //then
        final Member member = memberRepository.findById(memberId).get();
        final List<UnViewedCafe> memberUnViewedCafes = member.getUnViewedCafes();
        final List<Cafe> allCafes = cafeRepository.findAll();

        assertThat(memberUnViewedCafes)
                .extracting("cafe.id")
                .containsAll(allCafes.stream().map(Cafe::getId).toList());
    }

    @Test
    @DisplayName("Provider 인증 Url을 받는다.")
    void getAuthorizationUri() {
        //when
        final List<AuthorizationUrlDto> authorizationUrls = authService.getAuthorizationUrls();

        //then
        for (AuthorizationUrlDto provider : authorizationUrls) {
            assertThat(provider.authorizationUrl())
                    .contains("response_type", "redirect_uri", "client_id", "scope");
        }
    }

    private void saveCafes() {
        cafeRepository.save(Fixture.getCafe("name1", "address1", 1));
        cafeRepository.save(Fixture.getCafe("name2", "address2", 2));
    }
}
