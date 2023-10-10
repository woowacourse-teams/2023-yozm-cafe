package com.project.yozmcafe.controller.admin;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class AdminDocuments {

    public static RestDocumentationFilter adminCafeSaveDocument() {
        return document("어드민 API/카페 저장하기",
                requestPartBody("request"), requestPartBody("images"),
                responseHeaders(headerWithName("Location").description("카페 저장 위치")));
    }

    public static RestDocumentationFilter adminCafeUpdateDocument() {
        return document("어드민 API/카페 업데이트하기",
                requestPartBody("request"), requestPartBody("images"),
                pathParameters(parameterWithName("cafeId").description("업데이트할 카페 ID")));
    }

    public static RestDocumentationFilter adminCafeRemoveDocument() {
        return document("어드민 API/카페 삭제하기",
                pathParameters(parameterWithName("cafeId").description("삭제할 카페 ID")));
    }

    public static RestDocumentationFilter adminAllCafesFindDocument() {
        return document("어드민 API/카페 전체 조회하기",
                responseFields(
                        fieldWithPath("[].id").description("카페 ID"),
                        fieldWithPath("[].name").description("카페 이름"),
                        fieldWithPath("[].address").description("카페 도로명 주소"),
                        fieldWithPath("[].isLiked").description("좋아요 누른 여부"),
                        fieldWithPath("[].likeCount").description("좋아요 숫자"),
                        fieldWithPath("[].images.[]").description("이미지 URL 배열"),
                        fieldWithPath("[].detail.mapUrl").description("네이버 지도 URL"),
                        fieldWithPath("[].detail.description").description("상세 설명"),
                        fieldWithPath("[].detail.phone").description("전화번호"),
                        fieldWithPath("[].detail.openingHours.[].day").description("요일"),
                        fieldWithPath("[].detail.openingHours.[].open").description("영업 시작 시각"),
                        fieldWithPath("[].detail.openingHours.[].close").description("영업 종료 시각"),
                        fieldWithPath("[].detail.openingHours.[].opened").description("해당 요일 영업 여부")));
    }

    public static RestDocumentationFilter adminCafeFindDocument() {
        return document("어드민 API/카페 단건 조회하기",
                pathParameters(parameterWithName("cafeId").description("조회할 카페 ID")),
                responseFields(
                        fieldWithPath("id").description("카페 ID"),
                        fieldWithPath("name").description("카페 이름"),
                        fieldWithPath("address").description("카페 도로명 주소"),
                        fieldWithPath("isLiked").description("좋아요 누른 여부"),
                        fieldWithPath("likeCount").description("좋아요 숫자"),
                        fieldWithPath("images.[]").description("이미지 URL 배열"),
                        fieldWithPath("detail.mapUrl").description("네이버 지도 URL"),
                        fieldWithPath("detail.description").description("상세 설명"),
                        fieldWithPath("detail.phone").description("전화번호"),
                        fieldWithPath("detail.openingHours.[].day").description("요일"),
                        fieldWithPath("detail.openingHours.[].open").description("영업 시작 시각"),
                        fieldWithPath("detail.openingHours.[].close").description("영업 종료 시각"),
                        fieldWithPath("detail.openingHours.[].opened").description("해당 요일 영업 여부"))
        );
    }

    public static RestDocumentationFilter adminLocationSaveDocument() {
        return document("어드민 API/좌표 정보 저장",
                pathParameters(parameterWithName("cafeId").description("죄표를 추가할 카페 ID")),
                requestFields(
                        fieldWithPath("latitude").description("위도"),
                        fieldWithPath("longitude").description("경도")
                ),
                responseHeaders(headerWithName("Location").description("카페 저장 위치")));
    }
}
