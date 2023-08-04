package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.AvailableTimeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.controller.dto.cafe.DetailRequest;
import com.project.yozmcafe.domain.cafe.available.Days;
import com.project.yozmcafe.util.AcceptanceContext;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CafeAdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AcceptanceContext context;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("카페 저장하기")
    void save() {
        //given
        final CafeRequest request = makeCafeRequest();

        //when
        context.invokeHttpPost("/admin/cafes", request);

        //then
        context.response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("카페 업데이트")
    void update() {
        //given
        context.invokeHttpPost("/admin/cafes", makeCafeRequest());
        final String location = context.response.header("Location");

        //when
        context.invokeHttpPut(location, makeCafeUpdateRequest());

        //then
        context.response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("카페 삭제")
    void delete() {
        //given
        context.invokeHttpPost("/admin/cafes", makeCafeRequest());
        final String location = context.response.header("Location");

        //when
        context.invokeHttpDelete(location);

        //then
        context.response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("카페 전체 조회")
    void findAll() {
        //when
        context.invokeHttpGet("/admin/cafes");

        //then
        context.response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("카페 단건 조회")
    void findById() {
        //given
        context.invokeHttpPost("/admin/cafes", makeCafeRequest());
        final String location = context.response.header("Location");

        //when
        context.invokeHttpGet(location);

        //then
        context.response.then()
                .statusCode(HttpStatus.OK.value());
    }

    private CafeRequest makeCafeRequest() {
        return new CafeRequest("연어카페", "광안리",
                List.of("https://image5jvqbd.fmkorea.com/files/attach/new2/20221010/486616/3539993806/5096425812/9ccc34aeb4ea8d131d7f9b66919dfdec.jpg"),
                detail());
    }

    private CafeUpdateRequest makeCafeUpdateRequest() {
        return new CafeUpdateRequest("참치전문점", "해운대",
                List.of("https://image5jvqbd.fmkorea.com/files/attach/new2/20221010/486616/3539993806/5096425812/9ccc34aeb4ea8d131d7f9b66919dfdec.jpg"),
                detail(),
                100);
    }

    private DetailRequest detail() {
        final AvailableTimeRequest time1 = new AvailableTimeRequest(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time2 = new AvailableTimeRequest(Days.TUESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time3 = new AvailableTimeRequest(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time4 = new AvailableTimeRequest(Days.THURSDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time5 = new AvailableTimeRequest(Days.FRIDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time6 = new AvailableTimeRequest(Days.SATURDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time7 = new AvailableTimeRequest(Days.SUNDAY, LocalTime.now(), LocalTime.now(), true);

        return new DetailRequest(List.of(time1, time2, time3, time4, time5, time6, time7),
                "지도 url", "존맛탱구리", "01032472601");
    }
}
