package com.project.yozmcafe.controller.admin;

import static com.project.yozmcafe.controller.admin.AdminDocuments.adminAllCafesFindDocument;
import static com.project.yozmcafe.controller.admin.AdminDocuments.adminCafeFindDocument;
import static com.project.yozmcafe.controller.admin.AdminDocuments.adminCafeRemoveDocument;
import static com.project.yozmcafe.controller.admin.AdminDocuments.adminCafeSaveDocument;
import static com.project.yozmcafe.controller.admin.AdminDocuments.adminCafeUpdateDocument;
import static com.project.yozmcafe.controller.admin.AdminDocuments.adminLocationSaveDocument;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.MULTIPART;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.controller.dto.cafe.AvailableTimeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeCoordinateRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.controller.dto.cafe.DetailRequest;
import com.project.yozmcafe.domain.cafe.available.Days;

import io.restassured.response.Response;

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
        final Response response = customGivenWithDocs(adminCafeSaveDocument())
                .contentType(MULTIPART)
                .multiPart("request", cafeRequestJson, "application/json")
                .multiPart("images", image, "image/png")
                .accept(JSON)
                .post("/admin/cafes");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(CREATED.value());
            assertThat(response.header("Location")).isNotNull();
        });
    }

    @Test
    @DisplayName("카페 업데이트")
    void update() {
        //given
        doNothing().when(s3Client).upload(any(MultipartFile.class));
        doNothing().when(s3Client).delete(anyString());
        String location = saveCafe();
        String updateRequest = makeCafeUpdateRequest();

        //when
        final Response response = customGivenWithDocs(adminCafeUpdateDocument())
                .contentType(MULTIPART)
                .multiPart("request", updateRequest, "application/json")
                .multiPart("images", image, "image/png")
                .accept(JSON)
                .put("/admin/cafes/{cafeId}", location);

        //then
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
    }

    @Test
    @DisplayName("카페 삭제")
    void delete() {
        //given
        doNothing().when(s3Client).delete(anyString());
        String location = saveCafe();

        //when
        final Response response = customGivenWithDocs(adminCafeRemoveDocument())
                .delete("/admin/cafes/{cafeId}", location);

        //then
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
    }

    @Test
    @DisplayName("카페 전체 조회")
    void findAll() {
        //given
        saveCafe();
        saveCafe();

        //when, then
        final Response response = customGivenWithDocs(adminAllCafesFindDocument())
                .get("/admin/cafes");

        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("카페 단건 조회")
    void findById() {
        //given
        String location = saveCafe();

        //when
        final Response response = customGivenWithDocs(adminCafeFindDocument())
                .get("/admin/cafes/{cafeId}", location);

        //then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("좌표 정보를 저장한다.")
    void saveCoordinate() {
        //given
        final String location = saveCafe();

        //when
        final Response response = customGivenWithDocs(adminLocationSaveDocument())
                .body(new CafeCoordinateRequest(20, 10))
                .post("/admin/cafes/{cafeId}/coordinate", location);

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(CREATED.value());
            assertThat(response.header("Location")).isNotNull();
        });
    }

    private String saveCafe() {
        doNothing().when(s3Client).upload(any(MultipartFile.class));
        final String cafeRequest = makeCafeRequest();

        final String location = customGiven()
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
