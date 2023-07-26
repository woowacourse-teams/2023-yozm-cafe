package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.util.AcceptanceContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

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
        final Cafe save1 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Cafe save2 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        member.addLikedCafe(save1);
        member.addLikedCafe(save2);
        memberRepository.save(member);

        //when
        context.invokeHttpGet("/members/{memberId}/likedCafes?size=1&page=1", member.getId());
        Response response = context.response;
        context.invokeHttpGet("/members/{memberId}/likedCafes?size=1&page=2", member.getId());
        Response response2 = context.response;

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getBoolean("first")).isTrue(),
                () -> assertThat(response.jsonPath().getBoolean("last")).isFalse(),
                () -> assertThat(response2.jsonPath().getBoolean("first")).isFalse(),
                () -> assertThat(response2.jsonPath().getBoolean("last")).isTrue()
        );
    }

    @Test
    @DisplayName("멤버의 빈 좋아요 목록을 조회한다.")
    void getLikedCafes_empty() {
        //given
        final Member member = new Member("1", "오션", "오션.img");
        memberRepository.save(member);

        //when
        context.invokeHttpGet("/members/{memberId}/likedCafes?size=1&page=1", member.getId());
        Response response = context.response;

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getList("content")).isEmpty(),
                () -> assertThat(response.jsonPath().getBoolean("empty")).isTrue()
        );
    }
}
