package test.api;

import com.github.javafaker.Faker;
import com.miglesias.api.model.GeoapifyMap;
import com.miglesias.api.model.Marker;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import test.api.util.ApiExtension;


@ExtendWith(ApiExtension.class)
public class AddMap {

    @Test
    public void addAMapTest(RequestSpecification given) {
        Faker faker = new Faker();
        String mapName = faker.name().name();
        String area = faker.address().longitude() + "," + faker.address().latitude();
        String style = "osm-bright-smooth";
        String color = faker.color().hex();
        String icon = "dragon";
        String size = "medium";
        String type = "awesome";
        Marker marker = new Marker(color, icon, area, size, type);
        GeoapifyMap map = new GeoapifyMap(mapName, area, style, 640, 1020, marker);

        given.contentType("application/json").body(map).when().post("/map").then().statusCode(200);
    }

}
