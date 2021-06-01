package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;
import test.api.util.GeoapifyMapUtils;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApiExtension.class)
public class UpdateMapTests {

    @Test
    public void updateMapTest(RequestSpecification requestSpecification){
        GeoapifyMap map = GeoapifyMapUtils.createMap(600, 800);
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(201);
       Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("update map"))
                .queryParam("style", "klokantech-basic")
                .when().patch("/map/" + map.getMapName());

        Allure.step("Verify status code is 200");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void updateMapNotFoundTest(RequestSpecification requestSpecification){
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("update map"))
                .queryParam("style", "klokantech-basic")
                .when().patch("/map/" + "00000000000000000000000");

        Allure.step("Verify status code is 304");
        assertEquals(304, response.getStatusCode());
    }

    @Disabled("Fails and needs a fix")
    @Test
    public void updateMapBreakValidationTest(RequestSpecification requestSpecification){
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("update map"))
                .queryParam("style", "abc")
                .when().patch("/map/" + "00000000000000000000000");

        Allure.step("Verify status code is 400 because of style validation");
        assertEquals(400, response.getStatusCode());
    }
}
