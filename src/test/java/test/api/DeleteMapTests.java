package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;
import test.api.util.GeoapifyMapUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApiExtension.class)
public class DeleteMapTests {

    GeoapifyMap map;

    @Test
    public void deleteAMapTest(RequestSpecification requestSpecification) {
        map = GeoapifyMapUtils.createMap(600, 800);
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(201);
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete map"))
                .queryParam("mapNames", Collections.singletonList(map.getMapName()))
                .queryParam("secretKey", "ab@sk#!21")
                .when().delete("/map");

        Allure.step("Verify status code is 200 and map is deleted");
        assertEquals(200, response.getStatusCode());
        assertEquals("Deleted 1 maps", response.jsonPath().getString("response"));

        response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(map.getMapName()))
                .when().get("/map");
        Allure.step("Verify status code is 200 and map is deleted");
        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void deleteMultipleMapsTest(RequestSpecification requestSpecification) {
        List<String> mapsToDelete = new ArrayList<>();
        int timesToCreate = 0;

        while (timesToCreate++ < 2) {
            map = GeoapifyMapUtils.createMap(600, 800);
            given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map: " + map.getMapName()))
                    .body(map)
                    .when().post("/map")
                    .then().statusCode(201);
            mapsToDelete.add(map.getMapName());
        }
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete maps"))
                .queryParam("mapNames", mapsToDelete)
                .queryParam("secretKey", "ab@sk#!21")
                .when().delete("/map");

        Allure.step("Verify status code is 200 and maps are deleted");
        assertEquals(200, response.getStatusCode());
        assertEquals("Deleted 2 maps", response.jsonPath().getString("response"));
    }

    @Test
    public void deleteIncorrectKeyTest(RequestSpecification requestSpecification) {
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete map"))
                .queryParam("mapNames", Collections.singletonList(""))
                .queryParam("secretKey", "21")
                .when().delete("/map");

        Allure.step("Verify status code is 401");
        assertEquals(401, response.getStatusCode());
    }

    @Test
    public void deleteAMapIncorrectParamTest(RequestSpecification requestSpecification) {
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete map"))
                .queryParam("mapName", Collections.singletonList(""))
                .queryParam("secretKey", "ab@sk#!21")
                .when().delete("/map");

        Allure.step("Verify status code is 400");
        assertEquals(400, response.getStatusCode());
    }
}
