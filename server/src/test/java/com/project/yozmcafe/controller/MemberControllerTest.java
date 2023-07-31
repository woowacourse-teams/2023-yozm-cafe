package com.project.yozmcafe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;
import com.project.yozmcafe.util.AcceptanceContext;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:truncate.sql"}, executionPhase = AFTER_TEST_METHOD)
class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private AcceptanceContext context;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @SpyBean
    private KakaoOAuthClient kakaoOAuthClient;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void after() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("id로 멤버 조회")
    void findById() {
        //given
        final Member member = memberRepository.save(new Member("12413", "연어", "image"));
        final MemberResponse expected = MemberResponse.from(member);

        //when
        final MemberResponse response = RestAssured.given().log().all()
                .when()
                .get("/members/" + member.getId())
                .then()
                .extract().response().as(MemberResponse.class);

        //then
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @DisplayName("멤버의 좋아요 목록을 조회할 수 있다.")
    void getLikedCafes() {
        //given
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        member.addLikedCafe(savedCafe1);
        member.addLikedCafe(savedCafe2);
        memberRepository.save(member);

        //when
        context.invokeHttpGet("/members/{memberId}/liked-cafes?size=1&page=1", member.getId());
        Response response = context.response;
        context.invokeHttpGet("/members/{memberId}/liked-cafes?size=1&page=2", member.getId());
        Response response2 = context.response;

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getLong("[0].cafeId")).isEqualTo(savedCafe2.getId()),
                () -> assertThat(response2.jsonPath().getLong("[0].cafeId")).isEqualTo(savedCafe1.getId())
        );
    }

    @Test
    @DisplayName("멤버의 빈 좋아요 목록을 조회한다.")
    void getLikedCafes_empty() {
        //given
        final Member member = new Member("1", "오션", "오션.img");
        memberRepository.save(member);

        //when
        context.invokeHttpGet("/members/{memberId}/liked-cafes?size=1&page=1", member.getId());
        Response response = context.response;

        //then
        assertThat(response.jsonPath().getList("")).isEmpty();
    }

    @Test
    @DisplayName("재로그인 시 좋아요 목록이 남아있는다.")
    void reLoginLikedListTest() {
        //given
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Member member = memberRepository.save(
                new Member("memberId", "폴로", "폴로사진"));

        given(jwtTokenProvider.getMemberId(anyString())).willReturn(member.getId());
        doReturn(new MemberInfo(member.getId(), member.getName(), member.getImage())).when(kakaoOAuthClient)
                .getUserInfo(anyString());

        //when
        context.accessToken = "accessToken";
        context.invokeHttpPostWithToken("/cafes/" + cafe.getId() + "/likes?isLiked=true");
        relogin();
        context.invokeHttpGet("/members/{memberId}/liked-cafes?size=1&page=1", member.getId());

        final List<LikedCafeResponse> result = context.response.jsonPath().getList("");

        //then
        assertThat(result).hasSize(1);
    }

    private Response relogin() {
        return RestAssured.given()
                .log().all()
                .queryParam("code", "googleCode")
                .when()
                .post("/auth/{providerName}", "kakao");
    }
}
