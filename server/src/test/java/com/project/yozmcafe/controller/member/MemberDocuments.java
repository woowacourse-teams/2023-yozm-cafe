package com.project.yozmcafe.controller.member;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class MemberDocuments {

    public static RestDocumentationFilter findMemberDocument() {
        return document("멤버 조회",
                pathParameters(
                        parameterWithName("memberId").description("멤버 ID")
                ), responseFields(
                        fieldWithPath("id").description("멤버 아이디"),
                        fieldWithPath("name").description("멤버 이름"),
                        fieldWithPath("imageUrl").description("멤버 사진")
                )
        );
    }
}
