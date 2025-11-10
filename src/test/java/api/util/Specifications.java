package api.util;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class Specifications {

    static Configuration conf = ConfigFactory.create(Configuration.class);

    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(conf.getUrl())
                .setBasePath(conf.getPath())
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpec_Code_200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpec_Code_200_or_204() {
        return new ResponseSpecBuilder()
                .expectStatusCode(anyOf(is(200), is(204)))
                .build();
    }

    public static ResponseSpecification responseSpec_Code_Full() {
        return new ResponseSpecBuilder()
                .expectStatusCode(anyOf(is(200), is(204), is(404), is(500)))
                .build();
    }

    public static ResponseSpecification responseSpec_Code_500() {
        return new ResponseSpecBuilder()
                .expectStatusCode(500)
                .build();
    }

    public static void installSpecification(
            RequestSpecification request,
            ResponseSpecification response
    ) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
