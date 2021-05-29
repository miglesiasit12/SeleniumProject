package test.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miglesias.api.model.GeoapifyMap;
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


@ExtendWith(ApiExtension.class)
public class GetMapTests {

    @Test
    public void getMapTest(RequestSpecification requestSpecification)  {
        GeoapifyMap map = GeoapifyMapUtils.createMap(600, 800);
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(equalTo(201));

        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(map.getMapName()))
                .when().get("/map")
                .then().statusCode(200)
                .body("maps.mapName", hasItem(map.getMapName()));
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

        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get maps"))
                .queryParam("mapNames", mapsToGet)
                .when().get("/map")
                .then().statusCode(200).body("maps.mapName", hasItems(maps.get(0).getMapName(), maps.get(1).getMapName()));
    }

    @Test
    public void getMapNoneExistTest(RequestSpecification requestSpecification) {
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(""))
                .when().get("/map")
                .then().statusCode(404);
    }
}
