package com.project.yozmcafe.controller;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.MULTIPART;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.yozmcafe.controller.dto.cafe.AvailableTimeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeCoordinateRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.controller.dto.cafe.DetailRequest;
import com.project.yozmcafe.domain.cafe.available.Days;

class CafeAdminControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper mapper;

    private File image = new File("src/test/resources/image.png");

    @Test
    @DisplayName("카페 저장하기")
    void save() {
        //given
        doNothing().when(s3Client).upload(any(MultipartFile.class));
        final String cafeRequestJson = makeCafeRequest();

        //when, then
        given(spec).log().all()
                .contentType(MULTIPART)
                .multiPart("request", cafeRequestJson, "application/json")
                .multiPart("images", image, "image/png")
                .accept(JSON)
                .filter(document("어드민 API/카페 저장하기",
                        requestPartBody("request"), requestPartBody("images"),
                        responseHeaders(headerWithName("Location").description("카페 저장 위치"))))
                .when()
                .post("/admin/cafes")
                .then()
                .statusCode(is(HttpStatus.CREATED.value()))
                .header("Location", notNullValue());
    }

    @Test
    @DisplayName("카페 업데이트")
    void update() {
        //given
        doNothing().when(s3Client).upload(any(MultipartFile.class));
        doNothing().when(s3Client).delete(anyString());
        String location = saveCafe();
        String updateRequest = makeCafeUpdateRequest();

        //when, then
        given(spec).log().all()
                .contentType(MULTIPART)
                .multiPart("request", updateRequest, "application/json")
                .multiPart("images", image, "image/png")
                .accept(JSON)
                .filter(document("어드민 API/카페 업데이트하기",
                        requestPartBody("request"), requestPartBody("images"),
                        pathParameters(parameterWithName("cafeId").description("업데이트할 카페 ID"))))
                .when()
                .put("/admin/cafes/{cafeId}", location)
                .then()
                .statusCode(is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    @DisplayName("카페 삭제")
    void delete() {
        //given
        doNothing().when(s3Client).delete(anyString());
        String location = saveCafe();

        //when, then
        given(spec).log().all()
                .contentType(JSON)
                .filter(document("어드민 API/카페 삭제하기",
                        pathParameters(parameterWithName("cafeId").description("삭제할 카페 ID"))))
                .when()
                .delete("/admin/cafes/{cafeId}", location)
                .then()
                .statusCode(is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    @DisplayName("카페 전체 조회")
    void findAll() {
        //given
        saveCafe();
        saveCafe();

        //when, then
        given(spec).log().all()
                .contentType(JSON)
                .filter(document("어드민 API/카페 전체 조회하기",
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
                                fieldWithPath("[].detail.openingHours.[].opened").description("해당 요일 영업 여부"))
                ))
                .when()
                .get("/admin/cafes")
                .then()
                .statusCode(is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("카페 단건 조회")
    void findById() {
        //given
        String location = saveCafe();

        //when, then
        given(spec).log().all()
                .contentType(JSON)
                .filter(document("어드민 API/카페 단건 조회하기",
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
                ))
                .when()
                .get("/admin/cafes/{cafeId}", location)
                .then()
                .statusCode(is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("좌표 정보를 저장한다.")
    void saveCoordinate() {
        //given
        final String location = saveCafe();

        //when, then
        given(spec).log().all()
                .contentType(JSON)
                .filter(document("어드민 API/좌표 정보 저장",
                        pathParameters(parameterWithName("cafeId").description("죄표를 추가할 카페 ID")),
                        requestFields(
                                fieldWithPath("latitude").description("위도"),
                                fieldWithPath("longitude").description("경도")
                        ),
                        responseHeaders(headerWithName("Location").description("카페 저장 위치"))
                ))
                .when()
                .body(new CafeCoordinateRequest(20, 10))
                .post("/admin/cafes/{cafeId}/coordinate", location)
                .then()
                .statusCode(is(HttpStatus.CREATED.value()))
                .header("Location", notNullValue());
    }

    private String saveCafe() {
        doNothing().when(s3Client).upload(any(MultipartFile.class));
        final String cafeRequest = makeCafeRequest();

        final String location = given().log().all()
                .contentType(MULTIPART)
                .multiPart("request", cafeRequest, "application/json")
                .multiPart("images", image, "image/png")
                .accept(JSON)
                .when()
                .post("/admin/cafes")
                .then()
                .extract().header("Location");

        String[] split = location.split("/");

        return split[split.length - 1];
    }

    private String makeCafeRequest() {
        try {
            return mapper.writeValueAsString(new CafeRequest("연어카페", "광안리", detail()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String makeCafeUpdateRequest() {
        try {
            return mapper.writeValueAsString(new CafeUpdateRequest("참치전문점", "해운대", detail(), 100));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DetailRequest detail() {
        final AvailableTimeRequest time1 = new AvailableTimeRequest(Days.MONDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time2 = new AvailableTimeRequest(Days.TUESDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time3 = new AvailableTimeRequest(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time4 = new AvailableTimeRequest(Days.THURSDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time5 = new AvailableTimeRequest(Days.FRIDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time6 = new AvailableTimeRequest(Days.SATURDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time7 = new AvailableTimeRequest(Days.SUNDAY, LocalTime.now(), LocalTime.now(),
                true);

        return new DetailRequest(List.of(time1, time2, time3, time4, time5, time6, time7),
                "지도 url", "존맛탱구리", "01032472601");
    }
}
