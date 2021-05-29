package test.api;

import com.miglesias.api.model.GeoapifyMap;
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
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.core.IsEqual.equalTo;
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

        assertEquals(201, response.getStatusCode(), "Validate Status Code");
    }

    @Test
    public void addAMapMapNameBlankTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().mapName("").build();
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(400)
                .body("errors.fieldName", everyItem(equalTo("mapName")),
                        "errors.message", everyItem(equalTo("MapName is mandatory")));

    }

    @Test
    public void addAMapStyleIncorrectFormatTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().style("abc123").build();
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(400)
                .body("errors.fieldName", everyItem(equalTo("style")),
                        "errors.message", everyItem(equalTo("Styles accepted osm-carto,osm-bright,osm-bright-grey,osm-bright-smooth,klokantech-basic,osm-liberty,maptiler-3d,toner,toner-grey,positron,positron-blue,positron-red,dark-matter,dark-matter-brown,dark-matter-dark-grey,dark-matter-dark-purple,dark-matter-purple-roads,dark-matter-yellow-roads")));
        ;
    }

    @Test
    public void addAMapAreaIncorrectFormatTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().area("-.21,43g").build();
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(400)
                .body("errors.fieldName", everyItem(equalTo("area")),
                        "errors.message", everyItem(equalTo("Longitude and Latitude Separated by comma")));
    }

    @Test
    public void addAMapMarkerColorIncorrectFormatTest(RequestSpecification requestSpecification) {
        map = map.toBuilder().marker(map.getMarker().toBuilder().color("#9999999999").build()).build();
        given(requestSpecification).filter(new AllureRestAssured().setRequestAttachmentName("create map"))
                .body(map)
                .when().post("/map")
                .then().statusCode(400)
                .body("errors.fieldName", everyItem(equalTo("marker.color")),
                        "errors.message", everyItem(equalTo("marker color names allowed blue|red|green|purple|black|yellow")));
    }
}
