package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;
import test.api.util.GeoapifyMapUtils;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("api")
@Epic("Api Tests")
@Feature("ADD Map Endpoint")
@ExtendWith(ApiExtension.class)
public class AddMapTests {

    GeoapifyMap map;

    @BeforeEach
    public void createMap() {
        map = GeoapifyMapUtils.createMap(600, 800);
    }

    @Test
    @Description("Add a valid map object returns the map that was added and 201 status code")
    public void addAMapTest(RequestSpecification requestSpecification) {
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map");

        assertAll("Validate response body and status code",
                () -> assertEquals(201, response.getStatusCode()),
                () -> assertEquals(map.getMapName(), response.jsonPath().getString("mapName")),
                () -> assertEquals(map.getArea(), response.jsonPath().getString("area"), "area field in map object"),
                () -> assertEquals(map.getStyle(), response.jsonPath().getString("style"), "style field in map object"),
                () -> assertEquals(map.getWidth(), response.jsonPath().getInt("width"), "width field in map object"),
                () -> assertEquals(map.getHeight(), response.jsonPath().getInt("height"), "height field in map object"),
                () -> assertEquals(map.getMarker().getColor(), response.jsonPath().getString("marker.color"), "marker.color field in map object"),
                () -> assertEquals(map.getMarker().getLonlat(), response.jsonPath().getString("marker.lonlat"), "marker.lonlat field in map object"),
                () -> assertEquals(map.getMarker().getIcon(), response.jsonPath().getString("marker.icon"), "marker.icon field in map object"),
                () -> assertEquals(map.getMarker().getSize(), response.jsonPath().getString("marker.size"), "marker.size field in map object"),
                () -> assertEquals(map.getMarker().getType(), response.jsonPath().getString("marker.type"), "marker.type field in map object")
        );
    }

    @Test
    @Description("Add a map with a blank map name returns a 400 and error messages returned")
    public void addAMapMapNameBlankTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().mapName("").build();
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map");

        assertAll("Validate response and status code",
                () -> assertEquals(400, response.getStatusCode()),
                () -> assertEquals("mapName", response.jsonPath().getString("errors[0].fieldName")),
                () -> assertEquals("MapName is mandatory", response.jsonPath().getString("errors[0].message"))
        );
    }

    @Test
    @Description("Add a map with a blank map name returns a 400 and error messages returned")
    public void addAMapStyleIncorrectFormatTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().style("abc123").build();
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map");

        assertAll("Validate response and status code",
                () -> assertEquals(400, response.getStatusCode()),
                () -> assertEquals("style", response.jsonPath().getString("errors[0].fieldName")),
                () -> assertEquals("Styles accepted osm-carto,osm-bright,osm-bright-grey,osm-bright-smooth,klokantech-basic,osm-liberty,maptiler-3d,toner,toner-grey,positron,positron-blue,positron-red,dark-matter,dark-matter-brown,dark-matter-dark-grey,dark-matter-dark-purple,dark-matter-purple-roads,dark-matter-yellow-roads", response.jsonPath().getString("errors[0].message"))
        );
    }

    @Test
    @Description("Add a map with an invalid area field returns a 400 and error messages returned")
    public void addAMapAreaIncorrectFormatTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().area("-.21,43g").build();
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map");

        assertAll("Validate response and status code",
                () -> assertEquals(400, response.getStatusCode()),
                () -> assertEquals("area", response.jsonPath().getString("errors[0].fieldName")),
                () -> assertEquals("Longitude and Latitude Separated by comma", response.jsonPath().getString("errors[0].message"))
        );
    }

    @Test
    @Description("Add a map with an invalid color field returns a 400 and error messages returned")
    public void addAMapMarkerColorIncorrectFormatTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().marker(map.getMarker().toBuilder().color("#9999999999").build()).build();
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map");

        assertAll("Validate response and status code",
                () -> assertEquals(400, response.getStatusCode()),
                () -> assertEquals("marker.color", response.jsonPath().getString("errors[0].fieldName")),
                () -> assertEquals("marker color names allowed blue|red|green|purple|black|yellow", response.jsonPath().getString("errors[0].message"))
        );
    }
}
