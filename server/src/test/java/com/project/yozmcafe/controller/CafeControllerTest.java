package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.util.AcceptanceContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CafeControllerTest {

    public static final String MEMBER_ID = "memberId";

    @LocalServerPort
    private int port;
    @Autowired
    private AcceptanceContext context;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    private Cafe cafe1, cafe2, cafe3, cafe4, cafe5;

    @BeforeEach
    void setUp() {
        cafe1 = cafeRepository.save(Fixture.getCafe("n1", "address1", 1));
        cafe2 = cafeRepository.save(Fixture.getCafe("n2", "address2", 1));
        cafe3 = cafeRepository.save(Fixture.getCafe("n3", "address3", 1));
        cafe4 = cafeRepository.save(Fixture.getCafe("n4", "address4", 1));
        RestAssured.port = port;
    }

    @AfterEach
    public void afterEach() {
        memberRepository.deleteAll();
        cafeRepository.deleteAll();
    }

    @Test
    @DisplayName("카페에 좋아요를 추가하고, 해당 카페를 조회하는 경우 isLike가 true로 매핑되어 응답한다.")
    void updateLikesAdd() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);

        saveMemberAndUnViewedCafes();

        //when
        context.invokeHttpPostWithToken("/cafes/" + cafe1.getId() + "/likes?isLiked=true");
        final Response likeResponse = context.response;

        context.invokeHttpGetWithToken("/cafes");
        final List<CafeResponse> cafeResponses = context.response.jsonPath().getList(".", CafeResponse.class);
        final CafeResponse cafeResponse = getCafeResponse(cafeResponses, cafe1.getId());

        //then
        assertAll(
                () -> assertThat(likeResponse.getStatusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).hasSize(4),
                () -> assertThat(cafeResponse.likeCount()).isEqualTo(2),
                () -> assertThat(cafeResponse.isLiked()).isTrue()
        );
    }

    private CafeResponse getCafeResponse(final List<CafeResponse> cafeResponses, long targetCafeId) {
        return cafeResponses.stream()
                .filter(response -> Objects.equals(response.id(), targetCafeId))
                .findAny()
                .get();
    }

    @Test
    @DisplayName("카페에 좋아요를 취소하고, 해당 카페를 조회하는 경우 isLike가 false로 매핑되어 응답한다.")
    void updateLikes() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);
        saveMemberAndUnViewedCafesAndLikedCafes();

        //when
        context.invokeHttpPostWithToken("/cafes/" + cafe1.getId() + "/likes?isLiked=false");
        final Response likeResponse = context.response;

        context.invokeHttpGetWithToken("/cafes");
        final List<CafeResponse> cafeResponses = context.response.jsonPath().getList(".", CafeResponse.class);
        final CafeResponse cafeResponse = getCafeResponse(cafeResponses, cafe1.getId());

        //then
        assertAll(
                () -> assertThat(likeResponse.getStatusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).hasSize(4),
                () -> assertThat(cafeResponse.likeCount()).isEqualTo(0),
                () -> assertThat(cafeResponse.isLiked()).isFalse()
        );
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes/guest?page=? 에 GET요청을 보내면 페이지에 해당하는 서로 다른 카페 정보들을 5개씩 응답한다.")
    void getCafesSuccessByUnLoginUser() {
        //given
        cafe5 = cafeRepository.save(Fixture.getCafe("n5", "address5", 1));

        //when
        context.invokeHttpGet("/cafes/guest?page=1");

        //then
        List<CafeResponse> cafeResponses = context.response.jsonPath().getList(".", CafeResponse.class);
        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).extracting("id", "name")
                        .contains(tuple(cafe1.getId(), "n1"),
                                tuple(cafe2.getId(), "n2"),
                                tuple(cafe3.getId(), "n3"),
                                tuple(cafe4.getId(), "n4"),
                                tuple(cafe5.getId(), "n5"))
        );
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes/guest?page=? 에 GET요청을 보낸 경우 page가 최대 page를 초과하는 경우 빈배열을 반환한다.")
    void getCafesEmptyByUnLoginUser() {
        //when
        context.invokeHttpGet("/cafes/guest?page=2000");

        //then
        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(200),
                () -> assertThat(context.response.jsonPath().getList("$")).isEmpty()
        );
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes?page=?에 GET 요청을 보내면 아직보지않은 랜덤한,서로 다른 카페정보를 5개씩 응답한다.")
    void getCafesWithMember() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);

        final Member member = saveMemberAndUnViewedCafes();
        cafe5 = cafeRepository.save(Fixture.getCafe("n5", "address5", 1));
        member.addUnViewedCafes(List.of(cafe5));
        memberRepository.save(member);

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");

        //then
        List<CafeResponse> cafeResponses = context.response.jsonPath().getList(".", CafeResponse.class);

        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).extracting("id", "name")
                        .contains(tuple(cafe1.getId(), "n1"),
                                tuple(cafe2.getId(), "n2"),
                                tuple(cafe3.getId(), "n3"),
                                tuple(cafe4.getId(), "n4"),
                                tuple(cafe5.getId(), "n5"))
        );
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes?page=?에 GET 요청을 보낼 때, 아직보지 않은 카페가 5개 미만이면 그 수만큼의 서로 다른 카페를 응답한다.")
    void getCafesWithMemberWhenCafeLessThan() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);
        saveMemberAndUnViewedCafes();

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");

        //then
        List<CafeResponse> cafeResponses = context.response.jsonPath().getList(".", CafeResponse.class);

        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).extracting("id", "name")
                        .contains(tuple(cafe1.getId(), "n1"),
                                tuple(cafe2.getId(), "n2"),
                                tuple(cafe3.getId(), "n3"),
                                tuple(cafe4.getId(), "n4"))
        );
    }

    private Member saveMemberAndUnViewedCafes() {
        final Member member = new Member(MEMBER_ID, "asdf", "img");
        member.addUnViewedCafes(Arrays.asList(cafe1, cafe2, cafe3, cafe4));
        return memberRepository.save(member);
    }

    private void saveMemberAndUnViewedCafesAndLikedCafes() {
        final Member member = new Member(MEMBER_ID, "memberName", "memberImg");
        member.addUnViewedCafes(Arrays.asList(cafe1, cafe2, cafe3, cafe4));
        member.addLikedCafe(cafe1);
        member.addLikedCafe(cafe2);
        member.addLikedCafe(cafe3);
        member.addLikedCafe(cafe4);
        memberRepository.save(member);
    }
}
