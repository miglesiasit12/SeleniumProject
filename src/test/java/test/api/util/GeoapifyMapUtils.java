package test.api.util;

import com.github.javafaker.Faker;
import com.miglesias.api.model.GeoapifyMap;
import com.miglesias.api.model.Marker;
import io.qameta.allure.Allure;

import java.util.UUID;


public class GeoapifyMapUtils {

    public static GeoapifyMap createMap(int width, int height) {
        Faker faker = new Faker();
        String mapName = UUID.randomUUID().toString();
        String area = faker.address().longitude() + "," + faker.address().latitude();
        String style = "osm-bright-smooth";
        String color = "blue";
        String icon = "dragon";
        String size = "medium";
        String type = "awesome";
        Marker marker = new Marker(color, area, icon, size, type);


        Allure.getLifecycle().updateTestCase(Allure.getLifecycle().getCurrentTestCase().get(), testResult -> testResult.setDescription(testResult.getDescription() + "<br>Map Name Created:"  + mapName));
        return new GeoapifyMap(mapName, area, style, width, height, marker);
    }
}
