package com.project.yozmcafe.controller.location;

import static com.project.yozmcafe.controller.location.LocationDocuments.findCafesInMapDocument;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.controller.dto.cafe.AvailableTimeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeCoordinateRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeLocationResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.DetailRequest;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.available.Days;

import io.restassured.response.Response;

class LocationControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    CafeRepository cafeRepository;

    @Test
    @DisplayName("입력받은 좌표와 델타 좌표로 범위내에 있는 모든 카페를 반환한다.")
    void findCafesFromLocation() {
        //given
        doNothing().when(s3Client).upload(any());
        saveCafesAndCoordinates();

        //when
        final Response response = customGivenWithDocs(findCafesInMapDocument())
                .queryParam("latitude", 20)
                .queryParam("longitude", 10)
                .queryParam("latitudeDelta", 0.004504504505)
                .queryParam("longitudeDelta", 1)
                .get("/cafes/location");

        //then
        final List<CafeLocationResponse> results = response.jsonPath().getList(".", CafeLocationResponse.class);
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(results).hasSize(6);
        });
    }

    private void saveCafesAndCoordinates() {
        final String cafeId1 = saveCafe("폴로카페", "영도");
        final String cafeId2 = saveCafe("솔스카페", "기장");
        final String cafeId3 = saveCafe("오션카페", "인천");
        final String cafeId4 = saveCafe("도치카페", "서울역");
        final String cafeId5 = saveCafe("연어카페", "부산");
        final String cafeId6 = saveCafe("고니카페", "강동");
        final String cafeId7 = saveCafe("아인카페", "아르헨티나");
        saveLocation(cafeId1, 20.001, 10);
        saveLocation(cafeId2, 20.002, 10);
        saveLocation(cafeId3, 20.003, 10);
        saveLocation(cafeId4, 20.004, 10);
        saveLocation(cafeId5, 20.005, 10);
        saveLocation(cafeId6, 20.006, 10);
        saveLocation(cafeId7, 30.007, 10);
    }

    private String saveCafe(final String name, final String address) {
        final File image = new File("src/test/resources/image.png");
        final String cafeRequest = makeCafeRequest(name, address);
        final String location = customGiven()
                .contentType("multipart/form-data;charset=UTF-8")
                .multiPart("request", cafeRequest, "application/json")
                .multiPart("images", image, "image/png")
                .when()
                .post("/admin/cafes")
                .then()
                .extract().header("Location");

        String[] split = location.split("/");

        return split[split.length - 1];
    }

    private String makeCafeRequest(final String name, final String address) {
        try {
            return mapper.writeValueAsString(new CafeRequest(name, address, detail()));
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

    private void saveLocation(final String cafeId, final double latitude, final double longitude) {
        given()
                .contentType(JSON)
                .body(new CafeCoordinateRequest(latitude, longitude))
                .when()
                .post("/admin/cafes/{cafeId}/coordinate", cafeId);
    }
}
