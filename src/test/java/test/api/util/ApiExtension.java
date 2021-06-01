package test.api.util;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.*;

import static io.restassured.RestAssured.given;

public class ApiExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == RequestSpecification.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return given()
                .baseUri(System.getenv("mapApiBaseUrl"))
                .basePath(System.getenv("mapApiBasePath"))
                .port(Integer.parseInt(System.getenv("mapApiPort")))
                .contentType("application/json");
    }
}
