package com.project.yozmcafe.controller.member;

import static com.project.yozmcafe.controller.member.MemberDocuments.findMemberDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;

import io.restassured.response.Response;

class MemberControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("id로 멤버 조회")
    void findById() {
        //given
        final Member member = memberRepository.save(new Member("12413", "연어", "image"));

        //when
        final Response response = customGivenWithDocs(findMemberDocument())
                .get("/members/{memberId}", member.getId());

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(response.jsonPath().getString("id")).isEqualTo(member.getId());
        });
    }
}
