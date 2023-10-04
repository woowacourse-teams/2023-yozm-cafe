package com.project.yozmcafe.controller;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class BaseControllerTest extends BaseTest {

    @LocalServerPort
    private int port;
    @SpyBean
    protected GoogleOAuthClient googleOAuthClient;
    @SpyBean
    protected KakaoOAuthClient kakaoOAuthClient;
    @SpyBean
    protected MemberRepository memberRepository;
    @SpyBean
    protected S3Client s3Client;
    @SpyBean
    protected JwtTokenProvider jwtTokenProvider;

    protected RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation))
                .build();
    }
}
