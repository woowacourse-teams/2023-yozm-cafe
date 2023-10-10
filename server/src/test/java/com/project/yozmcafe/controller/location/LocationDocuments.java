package com.project.yozmcafe.controller.location;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class LocationDocuments {

    public static RestDocumentationFilter findCafesInMapDocument() {
        return document("Location/지도 범위 내 모든 카페 조회",
                queryParameters(
                        parameterWithName("latitude").description("기준 위도"),
                        parameterWithName("longitude").description("기준 경도"),
                        parameterWithName("latitudeDelta").description("기준 위도로부터 지도 내 최고 위도까지의 차"),
                        parameterWithName("longitudeDelta").description("기준 경도로부터 지도 내 최고 경도까지의 차")
                ),
                responseFields(
                        fieldWithPath("[].id").description("카페 Id"),
                        fieldWithPath("[].name").description("카페 이름"),
                        fieldWithPath("[].address").description("카페 주소"),
                        fieldWithPath("[].latitude").description("카페의 위도"),
                        fieldWithPath("[].longitude").description("카페의 경도")
                ));
    }
}
