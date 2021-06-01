package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(ApiExtension.class)
public class GetMapTests {

    @Test
    public void getMapTest(RequestSpecification requestSpecification) {
        GeoapifyMap map = GeoapifyMapUtils.createMap(600, 800);
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(equalTo(201));

        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(map.getMapName()))
                .when().get("/map");

        Allure.step("Verify status code and map name matches");
        assertEquals(200, response.getStatusCode());
        assertEquals(map.getMapName(), response.jsonPath().getString("maps[0].mapName"));
    }

    @Test
    public void getMapMultipleTest(RequestSpecification requestSpecification) {
        List<String> mapsToGet = new ArrayList<>();
        List<GeoapifyMap> maps = new ArrayList<>();
        int timesToCreate = 0;

        while (timesToCreate++ < 2) {
            GeoapifyMap map = GeoapifyMapUtils.createMap(600, 800);
            given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map: " + map.getMapName()))
                    .body(map)
                    .when().post("/map")
                    .then().statusCode(201);
            mapsToGet.add(map.getMapName());
            maps.add(map);
        }
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get maps"))
                .queryParam("mapNames", mapsToGet)
                .when().get("/map");

        Allure.step("Verify status code and map names match");
        assertEquals(200, response.getStatusCode());
        assertAll("Validate Response body array",
                () -> assertEquals(maps.get(0).getMapName(), response.jsonPath().getString("maps[0].mapName")),
                () -> assertEquals(maps.get(1).getMapName(), response.jsonPath().getString("maps[1].mapName"))
        );
    }

    @Test
    public void getMapNoneExistTest(RequestSpecification requestSpecification) {
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(""))
                .when().get("/map");

        Allure.step("Verify status code is 404");
        assertEquals(404, response.getStatusCode());
    }
}
