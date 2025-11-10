package api.tests;

import api.util.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    Configuration configuration = ConfigFactory.create(Configuration.class);
    protected List<String> createdUserIds = new ArrayList<>();
    protected ObjectMapper objectMapper = new ObjectMapper();

    final RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(configuration.getUrl())
            .setBasePath(configuration.getPath())
            .setContentType(ContentType.JSON).build();

    @BeforeEach
    public void setUp() {
        createdUserIds.clear();
    }

    @AfterEach
    public void tearDown() {
        for (String userId : createdUserIds) {
            try {
                given(spec)
                        .delete("/delete/{id}", userId)
                        .then()
                        .statusCode(HttpStatus.SC_OK);
            } catch (Exception e) {
                System.out.println("Failed to delete user with id: " + userId);
            }
        }
    }

    protected String createUserAndGetId(Object userData) {
        String userId = given(spec)
                .body(userData)
                .post("/create")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id")
                .toString();

        createdUserIds.add(userId);
        return userId;
    }
}