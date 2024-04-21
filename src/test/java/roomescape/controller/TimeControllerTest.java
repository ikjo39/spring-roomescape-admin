package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.TimeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TimeControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private TimeDto timeDto;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        String startAt = "15:40";
        timeDto = new TimeDto(null, startAt);
    }

    @Test
    @DisplayName("전체 예약 시간을 조회한다.")
    void getAllReservationsTest() {
        int count = getCount();
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(count));
    }

    @Test
    @DisplayName("예약 시간을 성공적으로 추가한다.")
    void addReservationTest() {
        int count = getCount();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeDto)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(count + 1));
    }

    private int getCount() {
        String sql = "SELECT COUNT(*) FROM reservation_time";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
