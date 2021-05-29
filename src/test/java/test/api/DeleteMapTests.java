package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;
import test.api.util.GeoapifyMapUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete map"))
                .queryParam("mapNames", Collections.singletonList(map.getMapName()))
                .queryParam("secretKey", "ab@sk#!21")
                .when().delete("/map")
                .then().statusCode(200).body("response", equalTo("Deleted 1 maps"));
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(map.getMapName()))
                .when().get("/map")
                .then().statusCode(404);
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
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete maps"))
                .queryParam("mapNames", mapsToDelete)
                .queryParam("secretKey", "ab@sk#!21")
                .when().delete("/map")
                .then().statusCode(200).body("response", equalTo("Deleted 2 maps"));
    }

    @Test
    public void deleteIncorrectKeyTest(RequestSpecification requestSpecification) {
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete map"))
                .queryParam("mapNames", Collections.singletonList(""))
                .queryParam("secretKey", "21")
                .when().delete("/map")
                .then().statusCode(401);
    }

    @Test
    public void deleteAMapIncorrectParamTest(RequestSpecification requestSpecification) {
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("delete map"))
                .queryParam("mapName", Collections.singletonList(""))
                .queryParam("secretKey", "ab@sk#!21")
                .when().delete("/map")
                .then().statusCode(400);
    }
}
