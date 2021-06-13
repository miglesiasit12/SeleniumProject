package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
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

@Tag("api")
@Epic("Api Tests")
@Feature("GET Map Endpoint")
@ExtendWith(ApiExtension.class)
public class GetMapTests {

    @Test
    @Description("Get map returns the map with the specified mapName and a 200 status code")
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
        assertAll( "Validate response status code and response body",
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertEquals(map.getMapName(), response.jsonPath().getString("maps[0].mapName")),
                () -> assertEquals(map.getArea(), response.jsonPath().getString("maps[0].area"), "area field in map object"),
                () -> assertEquals(map.getStyle(), response.jsonPath().getString("maps[0].style"), "style field in map object"),
                () -> assertEquals(map.getWidth(), response.jsonPath().getInt("maps[0].width"), "width field in map object"),
                () -> assertEquals(map.getHeight(), response.jsonPath().getInt("maps[0].height"), "height field in map object"),
                () -> assertEquals(map.getMarker().getColor(), response.jsonPath().getString("maps[0].marker.color"), "marker.color field in map object"),
                () -> assertEquals(map.getMarker().getLonlat(), response.jsonPath().getString("maps[0].marker.lonlat"), "marker.lonlat field in map object"),
                () -> assertEquals(map.getMarker().getIcon(), response.jsonPath().getString("maps[0].marker.icon"), "marker.icon field in map object"),
                () -> assertEquals(map.getMarker().getSize(), response.jsonPath().getString("maps[0].marker.size"), "marker.size field in map object"),
                () -> assertEquals(map.getMarker().getType(), response.jsonPath().getString("maps[0].marker.type"), "marker.type field in map object")
        );
    }

    @Test
    @Description("Get map returns the maps with the multiple mapNames and a 200 status code")
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
                () -> assertEquals(maps.get(0).getArea(), response.jsonPath().getString("maps[0].area"), "area field in map object"),
                () -> assertEquals(maps.get(0).getStyle(), response.jsonPath().getString("maps[0].style"), "style field in map object"),
                () -> assertEquals(maps.get(0).getWidth(), response.jsonPath().getInt("maps[0].width"), "width field in map object"),
                () -> assertEquals(maps.get(0).getHeight(), response.jsonPath().getInt("maps[0].height"), "height field in map object"),
                () -> assertEquals(maps.get(0).getMarker().getColor(), response.jsonPath().getString("maps[0].marker.color"), "marker.color field in map object"),
                () -> assertEquals(maps.get(0).getMarker().getLonlat(), response.jsonPath().getString("maps[0].marker.lonlat"), "marker.lonlat field in map object"),
                () -> assertEquals(maps.get(0).getMarker().getIcon(), response.jsonPath().getString("maps[0].marker.icon"), "marker.icon field in map object"),
                () -> assertEquals(maps.get(0).getMarker().getSize(), response.jsonPath().getString("maps[0].marker.size"), "marker.size field in map object"),
                () -> assertEquals(maps.get(0).getMarker().getType(), response.jsonPath().getString("maps[0].marker.type"), "marker.type field in map object"),
                () -> assertEquals(maps.get(1).getMapName(), response.jsonPath().getString("maps[1].mapName")),
                () -> assertEquals(maps.get(1).getArea(), response.jsonPath().getString("maps[1].area"), "area field in map object"),
                () -> assertEquals(maps.get(1).getStyle(), response.jsonPath().getString("maps[1].style"), "style field in map object"),
                () -> assertEquals(maps.get(1).getWidth(), response.jsonPath().getInt("maps[1].width"), "width field in map object"),
                () -> assertEquals(maps.get(1).getHeight(), response.jsonPath().getInt("maps[1].height"), "height field in map object"),
                () -> assertEquals(maps.get(1).getMarker().getColor(), response.jsonPath().getString("maps[1].marker.color"), "marker.color field in map object"),
                () -> assertEquals(maps.get(1).getMarker().getLonlat(), response.jsonPath().getString("maps[1].marker.lonlat"), "marker.lonlat field in map object"),
                () -> assertEquals(maps.get(1).getMarker().getIcon(), response.jsonPath().getString("maps[1].marker.icon"), "marker.icon field in map object"),
                () -> assertEquals(maps.get(1).getMarker().getSize(), response.jsonPath().getString("maps[1].marker.size"), "marker.size field in map object"),
                () -> assertEquals(maps.get(1).getMarker().getType(), response.jsonPath().getString("maps[1].marker.type"), "marker.type field in map object")

        );
    }

    @Test
    @Description("GET map does not return maps if the mapName does not exist in the database and 404 is returned with no body")
    public void getMapNoneExistTest(RequestSpecification requestSpecification) {
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("get map"))
                .queryParam("mapNames", Collections.singletonList(""))
                .when().get("/map");

        Allure.step("Verify status code is 404");
        assertEquals(404, response.getStatusCode());
    }
}
