package com.project.yozmcafe.controller;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.service.auth.JwtTokenProvider;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class BaseControllerTest extends BaseTest {

    @LocalServerPort
    private int port;

    @SpyBean
    protected S3Client s3Client;
    @SpyBean
    protected JwtTokenProvider jwtTokenProvider;

    @Value("${log}")
    private boolean showLog;

    protected RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    protected RequestSpecification customGiven() {
        final RequestSpecification customGiven = given();
        if (showLog) {
            return customGiven.log().all();
        }
        return customGiven;
    }

    protected RequestSpecification customGivenWithDocs(Filter document) {
        final RequestSpecification customGiven = given(spec).filter(document);
        if (showLog) {
            return customGiven.log().all();
        }
        return customGiven;
    }
}
