package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;
import test.api.util.GeoapifyMapUtils;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("api")
@Epic("Api Tests")
@Feature("UPDATE Map Endpoint")
@ExtendWith(ApiExtension.class)
public class UpdateMapTests {

    @Test
    @Description("Update map returns the map with the original and updated fields and a 200 status code")
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
        assertEquals("klokantech-basic", response.jsonPath().getString("style"));
    }

    @Test
    @Description("Update map when the mapname does not exist in the database returns a 304 status code")
    public void updateMapNotFoundTest(RequestSpecification requestSpecification){
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("update map"))
                .queryParam("style", "klokantech-basic")
                .when().patch("/map/" + "00000000000000000000000");

        Allure.step("Verify status code is 304");
        assertEquals(304, response.getStatusCode());
    }


    @Test
    @Description("Update map when the updated field fails validation returns a 400 response code and error message")
    @Disabled("Fails and needs a fix due to a bug")
    public void updateMapBreakValidationTest(RequestSpecification requestSpecification){
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("update map"))
                .queryParam("style", "abc")
                .when().patch("/map/" + "00000000000000000000000");

        Allure.step("Verify status code is 400 because of style validation");
        assertEquals(400, response.getStatusCode());
    }
}
