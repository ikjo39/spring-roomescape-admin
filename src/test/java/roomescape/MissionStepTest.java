package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    @DisplayName("홈 화면을 요청하면 200 OK을 응답한다.")
    void adminPageTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }
}
