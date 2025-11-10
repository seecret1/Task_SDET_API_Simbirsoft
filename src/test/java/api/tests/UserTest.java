package api.tests;

import api.dto.Addition;
import api.dto.Entity;
import api.util.Specifications;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class UserTest {

    private final Random random = new Random();
    private final String URL = "http://localhost:8080/api/";
    private final List<String> createdEntityIds = new ArrayList<>();

    @AfterEach
    public void cleanUp() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_200_or_204()
        );

        for (String id : createdEntityIds) {
            given()
                    .when()
                    .delete("delete/" + id)
                    .then()
                    .log().all();
        }

        createdEntityIds.clear();
    }

    @Test
    public void getAllEntities() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_200()
        );

        List<Entity> users = given()
                .when()
                .get("getAll")
                .then().log().all()
                .extract().body().jsonPath().getList("entities", Entity.class);

        users.forEach(user -> Assertions.assertTrue(user.getTitle().contains("Заголовок сущности")));
    }

    @Test
    public void getAllEntities_Code500() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_500()
        );

        List<Entity> users = given()
                .when()
                .get("get/" + random.nextInt(10000, 100000))
                .then().log().all()
                .extract().body().jsonPath().getList("entities", Entity.class);
    }

    @Test
    public void getEntityById() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_200()
        );

        getById(random.nextInt(1, 5));
    }

    @Test
    public void createEntity() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_200()
        );

        String responseText = create(
                "Addition number 1",
                random.nextInt(1, 10000),
                "Simple title",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        createdEntityIds.add(responseText);

        Integer responseID = Integer.parseInt(responseText.trim());
        Assertions.assertNotNull(responseID);
        Assertions.assertTrue(responseText.equals(responseID.toString()));
    }

    @Test
    public void createEntityAndCheck() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_200()
        );

        String id = create(
                "Addition number 1",
                random.nextInt(1, 10000),
                "Simple title",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        createdEntityIds.add(id);

        getById(Integer.parseInt(id));
    }

    @Test
    public void updateEntity() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_200_or_204()
        );

        String id = create(
                "Original addition info",
                random.nextInt(1, 10000),
                "Original title",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        createdEntityIds.add(id);

        Addition updatedAddition = new Addition(
                "Updated addition info",
                random.nextInt(10001, 20000)
        );

        Entity updatedEntity = new Entity(
                "Updated title",
                false,
                updatedAddition,
                List.of(100, 200, 300)
        );

        String response = given()
                .body(updatedEntity)
                .when()
                .patch("patch/" + id)
                .then()
                .log().all()
                .extract().asString();
    }

    @Test
    public void deleteEntity() {
        Specifications.installSpecification(
                Specifications.requestSpec(URL),
                Specifications.responseSpec_Code_Full()
        );

        String id = create(
                "Addition for deletion",
                random.nextInt(1, 10000),
                "Title to delete",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        createdEntityIds.add(id);

        given()
                .when()
                .delete("delete/" + id)
                .then()
                .log().all();

        System.out.println("Entity deleted: " + id);

        given()
                .when()
                .get("get/" + id)
                .then()
                .statusCode(anyOf(is(404), is(500), is(200)))
                .log().all();

        createdEntityIds.remove(id);
    }


    private String create(String additionStr, int additionInt, String title, boolean verified, List<Integer> integers) {
        Addition addition = new Addition(additionStr, additionInt);

        Entity entity = new Entity(
                title,
                verified,
                addition,
                integers
        );

        return given()
                .body(entity)
                .when()
                .post("/create")
                .then().log().all()
                .extract().asString();
    }

    private Entity getById(int id) {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .get("get/" + id)
                .then().log().all()
                .extract().body().jsonPath().getObject("entity", Entity.class);
    }
}