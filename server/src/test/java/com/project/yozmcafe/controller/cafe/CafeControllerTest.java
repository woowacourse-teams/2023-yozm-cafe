package com.project.yozmcafe.controller.cafe;

import static com.project.yozmcafe.controller.cafe.CafeDocuments.cafeEmptyRankingDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.cafeLikeCountRankingDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.cafeSearchDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.findCafeDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.findCafeNotFoundDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.guestCafesDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.guestOverFlowPageCafeDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.memberFindCafesDocument;
import static com.project.yozmcafe.controller.cafe.CafeDocuments.memberFindCafesLessThanFiveDocument;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeSearchResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.fixture.Fixture;

import io.restassured.response.Response;

class CafeControllerTest extends BaseControllerTest {

    private static final String MEMBER_ID = "memberId";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MenuRepository menuRepository;
    private Cafe cafe1, cafe2, cafe3, cafe4, cafe5;

    @BeforeEach
    void setUp() {
        cafe1 = cafeRepository.save(Fixture.getCafe("n1", "address1", 1));
        cafe2 = cafeRepository.save(Fixture.getCafe("n2", "address2", 2));
        cafe3 = cafeRepository.save(Fixture.getCafe("n3", "address3", 3));
        cafe4 = cafeRepository.save(Fixture.getCafe("n4", "address4", 4));
    }

    @Test
    @DisplayName("카페에 좋아요를 추가하고, 해당 카페를 조회하는 경우 isLike가 true로 매핑되어 응답한다.")
    void updateLikesAdd() {
        //given
        doReturn(MEMBER_ID).when(jwtTokenProvider).getMemberId(anyString());
        doNothing().when(jwtTokenProvider).validate(anyString());
        saveMemberAndUnViewedCafes();

        //when
        final Response likeResponse = customGiven()
                .auth().oauth2("tmpToken")
                .post("/cafes/{cafeId}/likes?isLiked=true", cafe1.getId());

        final Response response = customGiven()
                .auth().oauth2("tmpToken")
                .get("/cafes");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);
        final CafeResponse cafeResponse = getCafeResponse(cafeResponses, cafe1.getId());

        assertSoftly(softAssertions -> {
            assertThat(likeResponse.getStatusCode()).isEqualTo(200);
            assertThat(cafeResponses).hasSize(4);
            assertThat(cafeResponse.likeCount()).isEqualTo(2);
            assertThat(cafeResponse.isLiked()).isTrue();
        });
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
        doReturn(MEMBER_ID).when(jwtTokenProvider).getMemberId(anyString());
        doNothing().when(jwtTokenProvider).validate(anyString());
        saveMemberAndUnViewedCafesAndLikedCafes();

        //when
        final Response likeResponse = customGiven()
                .auth().oauth2("tmpToken")
                .post("/cafes/{cafeId}/likes?isLiked=false", cafe1.getId());

        final Response response = customGiven()
                .auth().oauth2("tmpToken")
                .get("/cafes");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);
        final CafeResponse cafeResponse = getCafeResponse(cafeResponses, cafe1.getId());

        assertSoftly(softAssertions -> {
            assertThat(likeResponse.getStatusCode()).isEqualTo(200);
            assertThat(cafeResponses).hasSize(4);
            assertThat(cafeResponse.likeCount()).isZero();
            assertThat(cafeResponse.isLiked()).isFalse();
        });
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes/guest?page=? 에 GET요청을 보내면 페이지에 해당하는 서로 다른 카페 정보들을 5개씩 응답한다.")
    void getCafesSuccessByUnLoginUser() {
        //given
        cafe5 = cafeRepository.save(Fixture.getCafe("n5", "address5", 1));

        //when
        final Response response = customGivenWithDocs(guestCafesDocument())
                .queryParam("page", 1)
                .get("/cafes/guest");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeResponses).extracting("id")
                    .contains(cafe1.getId(), cafe2.getId(), cafe3.getId(), cafe4.getId(), cafe5.getId());
        });
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes/guest?page=? 에 GET요청을 보낸 경우 page가 최대 page를 초과하는 경우 빈배열을 반환한다.")
    void getCafesEmptyByUnLoginUser() {
        //when
        final Response response = customGivenWithDocs(guestOverFlowPageCafeDocument())
                .get("/cafes/guest?page=2000");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeResponses).isEmpty();
        });
    }

    @Test
    @DisplayName("카페 id로 해당하는 카페를 조회한다.")
    void findById() {
        //when
        final Response response = customGivenWithDocs(findCafeDocument())
                .get("/cafes/{cafeId}", cafe3.getId());

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.jsonPath().getLong("id")).isEqualTo(cafe3.getId());
        });
    }

    @Test
    @DisplayName("카페 id로 해당하는 카페를 조회할 때, 존재하지 않는 카페이면 statusCode 400을 응답한다")
    void findByIdWhenNotExist() {
        //when
        final Response response = customGivenWithDocs(findCafeNotFoundDocument())
                .get("/cafes/{cafeId}", 9999999L);

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(400);
            assertThat(response.jsonPath().getString("message")).isEqualTo(NOT_EXISTED_CAFE.getMessage());
        });
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes 에 GET 요청을 보내면 아직 보지 않은 랜덤한, 서로 다른 카페정보를 5개씩 응답한다.")
    void getCafesWithMember() {
        //given
        doReturn(MEMBER_ID).when(jwtTokenProvider).getMemberId(anyString());
        doNothing().when(jwtTokenProvider).validate(anyString());

        addFiveUnViewedCafes();

        //when
        final Response response = customGivenWithDocs(memberFindCafesDocument())
                .auth().oauth2("tmpToken")
                .get("/cafes");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);

        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeResponses).extracting("id")
                    .contains(cafe1.getId(), cafe2.getId(), cafe3.getId(), cafe4.getId(), cafe5.getId());

        });
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes 에 GET 요청을 보낼 때, 아직보지 않은 카페가 5개 미만이면 남은 수만큼의 서로 다른 카페를 응답한다.")
    void getCafesWithMemberWhenCafeLessThan() {
        //given
        doReturn(MEMBER_ID).when(jwtTokenProvider).getMemberId(anyString());
        doNothing().when(jwtTokenProvider).validate(anyString());
        saveMemberAndUnViewedCafes();

        //when
        final Response response = customGivenWithDocs(memberFindCafesLessThanFiveDocument())
                .auth().oauth2("tmpToken")
                .get("/cafes");

        //then
        final List<CafeResponse> cafeResponses = getCafeResponses(response);
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeResponses).extracting("id")
                    .contains(cafe1.getId(), cafe2.getId(), cafe3.getId(), cafe4.getId());
        });
    }

    @Test
    @DisplayName("/cafes/rank?page=? 요청을 보내면 좋아요 개수가 큰 순으로 페이지에 맞는 카페정보를 응답한다.")
    void getCafesOrderByLikeCount() {
        //given
        final Cafe cafe5 = cafeRepository.save(Fixture.getCafe("n5", "address5", 50));
        final Cafe cafe6 = cafeRepository.save(Fixture.getCafe("n6", "address5", 66));
        final Cafe cafe7 = cafeRepository.save(Fixture.getCafe("n7", "address5", 67));
        final Cafe cafe8 = cafeRepository.save(Fixture.getCafe("n8", "address5", 68));
        final Cafe cafe9 = cafeRepository.save(Fixture.getCafe("n9", "address5", 69));
        final Cafe cafe10 = cafeRepository.save(Fixture.getCafe("n10", "address5", 70));

        //when
        final Response response = customGivenWithDocs(cafeLikeCountRankingDocument())
                .get("/cafes/ranks?page=1");

        final List<CafeRankResponse> cafeRankResponses = getCafeRankResponses(response);

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeRankResponses).extracting("id")
                    .containsExactly(cafe10.getId(), cafe9.getId(), cafe8.getId(), cafe7.getId(), cafe6.getId(),
                            cafe5.getId(), cafe4.getId(), cafe3.getId(), cafe2.getId(), cafe1.getId()
                    );
        });
    }

    @Test
    @DisplayName("/cafes/rank?page=? 요청을 보낼 때, 좋아요 된 카페가 존재하지 않으면 빈 배열을 반환한다.")
    void getCafesOrderByLikeCountWhenNotExist() {
        //given
        cafeRepository.save(Fixture.getCafe("n5", "address5", 20));

        //when
        final Response response = customGivenWithDocs(cafeEmptyRankingDocument())
                .get("/cafes/ranks?page=2");

        //then
        final List<CafeRankResponse> cafeRankResponses = getCafeRankResponses(response);

        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeRankResponses).isEmpty();
        });
    }

    @Test
    @DisplayName("사용자가 검색 요청을 보내면, 검색어와 기준에 맞는 카페를 응답한다.")
    void getCafesBySearch() {
        //given
        menuRepository.save(Fixture.getMenu(cafe1, 1, "요즘커피"));

        //when
        final Response response = customGivenWithDocs(cafeSearchDocument())
                .queryParam("cafeName", "n1")
                .queryParam("menu", "요즘커피")
                .queryParam("address", "address")
                .get("/cafes/search");

        //then
        final List<CafeSearchResponse> cafeSearchResponses = getCafeSearchResponses(response);
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(cafeSearchResponses).extracting("id")
                    .containsOnly(cafe1.getId());
        });
    }

    @Test
    @DisplayName("사용자가 검색 요청을 보내면, 검색어와 기준에 맞는 카페를 응답한다. - 메뉴 검색하지 않을 경우")
    void getCafesBySearchWhenNotSearchMenu() {
        //given, when
        final Response response = customGiven()
                .queryParam("cafeName", "n2")
                .queryParam("address", "address")
                .get("/cafes/search");

        //then
        final List<CafeSearchResponse> cafeSearchResponses = getCafeSearchResponses(response);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeSearchResponses).extracting("id", "name")
                        .containsOnly(tuple(cafe2.getId(), "n2"))
        );
    }

    private void addFiveUnViewedCafes() {
        final Member member = saveMemberAndUnViewedCafes();
        cafe5 = cafeRepository.save(Fixture.getCafe("n5", "address5", 1));
        member.addUnViewedCafes(List.of(cafe5));
        memberRepository.save(member);
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

    private List<CafeResponse> getCafeResponses(final Response response) {
        return response.jsonPath().getList(".", CafeResponse.class);
    }

    private List<CafeSearchResponse> getCafeSearchResponses(final Response response) {
        return response.jsonPath().getList(".", CafeSearchResponse.class);
    }

    private List<CafeRankResponse> getCafeRankResponses(final Response response) {
        return response.jsonPath().getList(".", CafeRankResponse.class);
    }
}
