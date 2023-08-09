package com.project.yozmcafe.controller;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.QueryParametersSnippet;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.JwtTokenProvider;

import io.restassured.response.Response;

class CafeControllerTest extends BaseControllerTest {

    private static final String MEMBER_ID = "memberId";
    private static final String CAFE_API = "cafeApi/";

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
    }

    @Test
    @DisplayName("카페에 좋아요를 추가하고, 해당 카페를 조회하는 경우 isLike가 true로 매핑되어 응답한다.")
    void updateLikesAdd() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);
        saveMemberAndUnViewedCafes();

        final Response likeResponse = given()
                .auth().oauth2("tmpToken")
                .when()
                .post("/cafes/{cafeId}/likes?isLiked=true", cafe1.getId());

        //when
        final Response response = given()
                .auth().oauth2("tmpToken")
                .when()
                .get("/cafes");
        final List<CafeResponse> cafeResponses = getCafeResponses(response);

        //then
        final CafeResponse cafeResponse = getCafeResponse(cafeResponses, cafe1.getId());
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
        final Response likeResponse = given().auth().oauth2("tmpToken")
                .when()
                .post("/cafes/{cafeId}/likes?isLiked=false", cafe1.getId());

        final Response response = given()
                .auth().oauth2("tmpToken")
                .when()
                .get("/cafes");

        final List<CafeResponse> cafeResponses = getCafeResponses(response);

        //then
        final CafeResponse cafeResponse = getCafeResponse(cafeResponses, cafe1.getId());
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
        final Response response = given(spec).log().all()
                .filter(document(CAFE_API + "비회원 사용자 카페 조회", getPageRequestParam(), getCafeResponseFields()))
                .when()
                .get("/cafes/guest?page=1");
        final List<CafeResponse> cafeResponses = getCafeResponses(response);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
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
        final Response response = given(spec).log().all()
                .filter(document(CAFE_API + "비회원 사용자 카페 조회 page가 최대 page를 초과하면 빈 배열 반환",
                        getPageRequestParam(),
                        responseFields(fieldWithPath("[]").description("page가 초과하여 빈배열 반환"))))
                .when()
                .get("/cafes/guest?page=2000");

        final List<CafeResponse> cafeResponses = getCafeResponses(response);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).isEmpty()
        );
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes 에 GET 요청을 보내면 아직 보지 않은 랜덤한, 서로 다른 카페정보를 5개씩 응답한다.")
    void getCafesWithMember() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);

        final Member member = saveMemberAndUnViewedCafes();
        cafe5 = cafeRepository.save(Fixture.getCafe("n5", "address5", 1));
        member.addUnViewedCafes(List.of(cafe5));
        memberRepository.save(member);

        //when
        final Response response = given(spec).log().all()
                .auth().oauth2("tmpToken")
                .filter(document(CAFE_API + "로그인 한 사용자 카페 조회", getCafeResponseFields()))
                .when()
                .get("/cafes");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).extracting("id", "name")
                        .contains(tuple(cafe1.getId(), "n1"),
                                tuple(cafe2.getId(), "n2"),
                                tuple(cafe3.getId(), "n3"),
                                tuple(cafe4.getId(), "n4"),
                                tuple(cafe5.getId(), "n5"))
        );
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes 에 GET 요청을 보낼 때, 아직보지 않은 카페가 5개 미만이면 남은 수만큼의 서로 다른 카페를 응답한다.")
    void getCafesWithMemberWhenCafeLessThan() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(MEMBER_ID);
        saveMemberAndUnViewedCafes();

        //when
        final Response response = given(spec).log().all()
                .auth().oauth2("tmpToken")
                .filter(document(CAFE_API + "로그인 한 사용자 카페 조회 시 남은 카페가 5개 미만인 경우", getCafeResponseFields()))
                .when()
                .get("/cafes");

        final List<CafeResponse> cafeResponses = getCafeResponses(response);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponses).extracting("id", "name")
                        .contains(tuple(cafe1.getId(), "n1"),
                                tuple(cafe2.getId(), "n2"),
                                tuple(cafe3.getId(), "n3"),
                                tuple(cafe4.getId(), "n4"))
        );
    }

    private Member saveMemberAndUnViewedCafes() {
        final Member member = new Member(MEMBER_ID, "asdf", "img");
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3, cafe4));
        return memberRepository.save(member);
    }

    private void saveMemberAndUnViewedCafesAndLikedCafes() {
        final Member member = new Member(MEMBER_ID, "memberName", "memberImg");
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3, cafe4));
        member.updateLikedCafesBy(cafe1, true);
        member.updateLikedCafesBy(cafe2, true);
        member.updateLikedCafesBy(cafe3, true);
        member.updateLikedCafesBy(cafe4, true);
        memberRepository.save(member);
    }

    private ResponseFieldsSnippet getCafeResponseFields() {
        return responseFields(
                fieldWithPath("[].id").description("카페 아이디"),
                fieldWithPath("[].name").description("카페 이름"),
                fieldWithPath("[].address").description("카페 주소"),
                fieldWithPath("[].images[]").description("카페 이미지 url"),
                fieldWithPath("[].isLiked").description("해당 카페의 좋아요 여부(비회원은 default = false)"),
                fieldWithPath("[].likeCount").description("카페의 좋아요 갯수"),
                fieldWithPath("[].detail.phone").description("카페의 전화번호"),
                fieldWithPath("[].detail.mapUrl").description("카페의 네이버 지도 url"),
                fieldWithPath("[].detail.description").description("카페의 상세정보"),
                fieldWithPath("[].detail.openingHours[].day").description("요일"),
                fieldWithPath("[].detail.openingHours[].open").description("카페 오픈시간"),
                fieldWithPath("[].detail.openingHours[].close").description("카페 종료시간"),
                fieldWithPath("[].detail.openingHours[].opened").description("카페 해당요일 영업여부")
        );
    }

    private List<CafeResponse> getCafeResponses(final Response response) {
        return response.then().log().all()
                .extract().jsonPath().getList(".", CafeResponse.class);
    }

    private QueryParametersSnippet getPageRequestParam() {
        return queryParameters(parameterWithName("page").description("페이지 번호"));
    }
}
