package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

class MemberControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("id로 멤버 조회")
    void findById() {
        //given
        final Member member = memberRepository.save(new Member("12413", "연어", "image"));
        final MemberResponse expected = MemberResponse.from(member);

        //when
        final MemberResponse response = RestAssured.given(spec).log().all()
                .filter(document("멤버 조회",
                        pathParameters(
                                parameterWithName("memberId").description("멤버 ID")
                        ), responseFields(
                                fieldWithPath("id").description("멤버 아이디"),
                                fieldWithPath("name").description("멤버 이름"),
                                fieldWithPath("imageUrl").description("멤버 사진")
                        )
                ))
                .when()
                .get("/members/{memberId}", member.getId())
                .then()
                .extract().response().as(MemberResponse.class);

        //then
        assertThat(response).isEqualTo(expected);
    }
}
