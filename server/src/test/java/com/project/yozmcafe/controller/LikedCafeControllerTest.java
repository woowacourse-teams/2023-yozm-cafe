package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.List;

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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

class LikedCafeControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("사용자가 좋아요 한 카페들의 대표 이미지들을 조회한다.")
    void getLikedCafes() {
        //given
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        member.updateLikedCafesBy(savedCafe1, true);
        member.updateLikedCafesBy(savedCafe2, true);
        memberRepository.save(member);

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요 카페 대표 이미지 목록 조회",
                        queryParameters(parameterWithName("page").description("좋아요 목록 페이지 번호")),
                        pathParameters(parameterWithName("memberId").description("멤버 ID")),
                        responseFields(fieldWithPath("[].cafeId").description("카페 ID"),
                                fieldWithPath("[].imageUrl").description("카페 대표 사진"))))
                .when()
                .get("/members/{memberId}/liked-cafes?page=1", member.getId());

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getLong("[0].cafeId")).isEqualTo(savedCafe2.getId()),
                () -> assertThat(response.jsonPath().getLong("[1].cafeId")).isEqualTo(savedCafe1.getId())
        );
    }

    @Test
    @DisplayName("멤버의 빈 좋아요한 카페 대표 이미지들을 조회한다.")
    void getLikedCafes_empty() {
        //given
        final Member member = new Member("1", "오션", "오션.img");
        memberRepository.save(member);

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요 카페 대표 이미지 목록 조회- 비어 있는 경우",
                        queryParameters(parameterWithName("page").description("좋아요 목록 페이지 번호")),
                        pathParameters(parameterWithName("memberId").description("멤버 ID"))))
                .when()
                .get("/members/{memberId}/liked-cafes?page=1", member.getId());

        //then
        assertThat(response.jsonPath().getList("")).isEmpty();
    }

    @Test
    @DisplayName("사용자가 카페에 대해 좋아요를 할 수 있다.")
    void updateLikes() {
        //given
        final Member member = memberRepository.save(
                new Member("123", "오션", "오션사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe(1L, "카페1", "주소1", 10));
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(member.getId());

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요",
                        queryParameters(parameterWithName("isLiked").description("true 일 경우 좋아요 추가, false 일 경우 좋아요 취소")),
                        pathParameters(parameterWithName("cafeId").description("카페 ID"))))
                .auth().oauth2("accessToken")
                .post("/cafes/{cafeId}/likes?isLiked=true", cafe.getId());

        //then
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("사용자의 좋아요 카페 목록의 카페들을 조회한다.")
    void getLikedCafeDetails() {
        //given
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "1동", 5));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("카페2", "2동", 5));
        cafeRepository.save(Fixture.getCafe("카페3", "3동", 7));
        final Member member = new Member("1234", "도치", "도치.img");
        member.updateLikedCafesBy(savedCafe1, true);
        member.updateLikedCafesBy(savedCafe2, true);
        Member savedMember = memberRepository.save(member);

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요 목록의 카페 조회",
                        pathParameters(parameterWithName("memberId").description("멤버 ID")),
                        getCafeResponseFields()))
                .when()
                .get("/members/{memberId}/liked-cafes/details", savedMember.getId());

        //then
        List<LikedCafeResponse> cafeDetailResponses = getCafeDetailResponses(response);

        assertThat(cafeDetailResponses).extracting("id", "name")
                .containsExactly(tuple(savedCafe2.getId(), savedCafe2.getName()),
                        tuple(savedCafe1.getId(), savedCafe1.getName())
                );
    }

    @Test
    @DisplayName("사용자의 빈 좋아요 카페 목록의 카페들을 조회한다.")
    void getLikedCafeDetailsWhenEmpty() {
        //given
        cafeRepository.save(Fixture.getCafe("카페1", "1동", 5));
        final Member savedMember = memberRepository.save(new Member("1234", "도치", "도치.img"));

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요 목록의 카페 조회 - 비어 있는 경우",
                        pathParameters(parameterWithName("memberId").description("멤버 ID"))))
                .when()
                .get("/members/{memberId}/liked-cafes/details", savedMember.getId());

        //then
        assertThat(response.jsonPath().getList("")).isEmpty();
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

    private List<LikedCafeResponse> getCafeDetailResponses(final Response response) {
        return response.then().log().all()
                .extract().jsonPath().getList(".", LikedCafeResponse.class);
    }
}
