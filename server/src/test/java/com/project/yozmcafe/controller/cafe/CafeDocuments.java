package com.project.yozmcafe.controller.cafe;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.QueryParametersSnippet;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class CafeDocuments {

    private static final String CAFE_API = "cafeApi/";

    public static RestDocumentationFilter guestCafesDocument() {
        return document(CAFE_API + "비회원 사용자 카페 조회", getPageRequestParam(), getCafeResponseFields());
    }

    public static RestDocumentationFilter guestOverFlowPageCafeDocument() {
        return document(CAFE_API + "비회원 사용자 카페 조회 page가 최대 page를 초과하면 빈 배열 반환",
                getPageRequestParam(),
                responseFields(fieldWithPath("[]").description("page가 초과하여 빈배열 반환")));
    }

    public static RestDocumentationFilter findCafeDocument() {
        return document(CAFE_API + "카페 id로 단 건 조회",
                pathParameters(parameterWithName("cafeId").description("카페 Id")),
                responseFields(
                        fieldWithPath(".id").description("카페 아이디"),
                        fieldWithPath(".name").description("카페 이름"),
                        fieldWithPath(".address").description("카페 주소"),
                        fieldWithPath(".images[]").description("카페 이미지 url"),
                        fieldWithPath(".isLiked").description("해당 카페의 좋아요 여부(비회원은 default = false)"),
                        fieldWithPath(".likeCount").description("카페의 좋아요 갯수"),
                        fieldWithPath(".detail.phone").description("카페의 전화번호"),
                        fieldWithPath(".detail.mapUrl").description("카페의 네이버 지도 url"),
                        fieldWithPath(".detail.description").description("카페의 상세정보"),
                        fieldWithPath(".detail.openingHours[].day").description("요일"),
                        fieldWithPath(".detail.openingHours[].open").description("카페 오픈시간"),
                        fieldWithPath(".detail.openingHours[].close").description("카페 종료시간"),
                        fieldWithPath(".detail.openingHours[].opened").description("카페 해당요일 영업여부")
                )
        );
    }

    public static RestDocumentationFilter findCafeNotFoundDocument() {
        return document(CAFE_API + "카페 id로 단 건 조회 예외",
                pathParameters(parameterWithName("cafeId").description("카페 Id")));
    }

    public static RestDocumentationFilter memberFindCafesDocument() {
        return document(CAFE_API + "로그인 한 사용자 카페 조회", getCafeResponseFields());
    }

    public static RestDocumentationFilter memberFindCafesLessThanFiveDocument() {
        return document(CAFE_API + "로그인 한 사용자 카페 조회 시 남은 카페가 5개 미만인 경우", getCafeResponseFields());
    }

    public static RestDocumentationFilter cafeLikeCountRankingDocument() {
        return document(CAFE_API + "좋아요 개수 순위에 따라 카페정보 조회",
                getPageRequestParam(),
                responseFields(
                        fieldWithPath("[].rank").description("카페 순위"),
                        fieldWithPath("[].id").description("카페 아이디"),
                        fieldWithPath("[].name").description("카페 이름"),
                        fieldWithPath("[].address").description("카페 주소"),
                        fieldWithPath("[].image").description("카페 대표 이미지 url"),
                        fieldWithPath("[].likeCount").description("카페의 좋아요 갯수")
                )
        );
    }

    public static RestDocumentationFilter cafeEmptyRankingDocument() {
        return document(CAFE_API + "좋아요 개수 순위에 따라 카페정보 조회 - 카페가 존재하지 않는 경우", getPageRequestParam());
    }

    public static RestDocumentationFilter cafeSearchDocument() {
        return document(CAFE_API + "카페 검색",
                queryParameters(
                        parameterWithName("cafeName").description("카페명 검색어"),
                        parameterWithName("menu").description("카페의 메뉴 검색어"),
                        parameterWithName("address").description("카페의 주소 검색어")),
                responseFields(
                        fieldWithPath("[].id").description("카페 아이디"),
                        fieldWithPath("[].name").description("카페 이름"),
                        fieldWithPath("[].address").description("카페 주소"),
                        fieldWithPath("[].image").description("카페 대표 이미지 url"),
                        fieldWithPath("[].likeCount").description("카페의 좋아요 갯수")));
    }

    private static QueryParametersSnippet getPageRequestParam() {
        return queryParameters(parameterWithName("page").description("페이지 번호"));
    }

    private static ResponseFieldsSnippet getCafeResponseFields() {
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
}
