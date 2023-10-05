package com.project.yozmcafe.controller.auth;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class AuthDocuments {
    public static RestDocumentationFilter oAuthLoginDocument() {
        return document("OAuth/OAuth 로그인",
                queryParameters(parameterWithName("code").description("Authorization Code")),
                pathParameters(parameterWithName("providerName").description("OAuth Provider")),
                responseFields(fieldWithPath("token").description("Access Token")),
                responseCookies(cookieWithName("refreshToken").description("Refresh Token")));
    }

    public static RestDocumentationFilter updateTokenDocument() {
        return document("OAuth/토큰 갱신",
                requestHeaders(headerWithName("Authorization").description("Access Token")),
                requestCookies(cookieWithName("refreshToken").description("Refresh Token")),
                responseFields(fieldWithPath("token").description("Access Token")),
                responseCookies(cookieWithName("refreshToken").description("Refresh Token")));
    }

    public static RestDocumentationFilter oAuthUrlDocument() {
        return document("OAuth/OAuth Provider Url",
                responseFields(fieldWithPath("[].provider").description("OAuth Provider 이름"),
                        fieldWithPath("[].authorizationUrl").description("Provider 인증 Url")));
    }

    public static RestDocumentationFilter logoutDocument() {
        return document("OAuth/OAuth 로그아웃",
                requestCookies(cookieWithName("refreshToken").description("Refresh Token")));
    }
}
