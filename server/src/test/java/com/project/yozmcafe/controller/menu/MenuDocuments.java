package com.project.yozmcafe.controller.menu;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class MenuDocuments {

    public static RestDocumentationFilter menuAndMenuBoardDocument() {
        return document("메뉴/메뉴 조회",
                pathParameters(
                        parameterWithName("cafeId").description("카페 ID")
                ), responseFields(
                        fieldWithPath("cafeId").description("카페 ID"),
                        fieldWithPath("menuBoards[].id").description("메뉴판 ID"),
                        fieldWithPath("menuBoards[].priority").description("메뉴판 우선순위"),
                        fieldWithPath("menuBoards[].imageUrl").description("메뉴판 이미지 url"),
                        fieldWithPath("menus[].id").description("메뉴 ID"),
                        fieldWithPath("menus[].priority").description("메뉴 우선순위"),
                        fieldWithPath("menus[].name").description("메뉴 이름"),
                        fieldWithPath("menus[].imageUrl").description("메뉴 이미지 url"),
                        fieldWithPath("menus[].description").description("메뉴 설명"),
                        fieldWithPath("menus[].price").description("메뉴 가격"),
                        fieldWithPath("menus[].isRecommended").description("대표 메뉴 여부")
                )
        );
    }
}
