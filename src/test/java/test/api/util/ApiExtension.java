package test.api.util;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static io.restassured.RestAssured.given;

public class ApiExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == RequestSpecification.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String baseUrl = System.getProperty("mapApiBaseUrl");

        System.out.println("Connecting to: " + baseUrl);
        return given()
                .baseUri(System.getProperty("mapApiBaseUrl"))
                .basePath(System.getProperty("mapApiBasePath"))
                .port(Integer.parseInt(System.getProperty("mapApiPort")))
                .contentType("application/json");
    }
}
