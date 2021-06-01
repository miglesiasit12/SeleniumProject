package test.api;

import com.miglesias.api.model.GeoapifyMap;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;
import test.api.util.GeoapifyMapUtils;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Feature("GeoApify Map")
@ExtendWith(ApiExtension.class)
public class AddMapTests {

    GeoapifyMap map;

    @BeforeEach
    public void createMap() {
        map = GeoapifyMapUtils.createMap(600, 800);
    }

    @Test
    public void addAMapTest(RequestSpecification requestSpecification) {
        Response response = given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map");

        Allure.step("Verify status code is 201");
        assertEquals(201, response.getStatusCode());
    }

    @Test
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
