package com.project.yozmcafe.controller.like;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class LikeCafeDocuments {

    public static RestDocumentationFilter findLikeCafesDocument() {
        return document("likedCafe/좋아요 카페 대표 이미지 목록 조회",
                queryParameters(parameterWithName("page").description("좋아요 목록 페이지 번호")),
                pathParameters(parameterWithName("memberId").description("멤버 ID")),
                responseFields(fieldWithPath("[].cafeId").description("카페 ID"),
                        fieldWithPath("[].imageUrl").description("카페 대표 사진")));
    }

    public static RestDocumentationFilter memberUpdateLikeDocument() {
        return document("likedCafe/좋아요",
                queryParameters(
                        parameterWithName("isLiked").description("true 일 경우 좋아요 추가, false 일 경우 좋아요 취소")),
                pathParameters(parameterWithName("cafeId").description("카페 ID")));
    }

    public static RestDocumentationFilter likedCafeOfMemberDocument() {
        return document("likedCafe/좋아요 목록의 카페 조회",
                pathParameters(parameterWithName("memberId").description("멤버 ID")),
                responseFields(
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
                ));
    }
}
