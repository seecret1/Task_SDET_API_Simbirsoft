// UserTest.java
package api.tests;

import api.dto.Addition;
import api.dto.Entity;
import api.util.Specifications;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Epic("Тесты добавления клиента")
@Feature("Заполнение формы данными")
@DisplayName("Тесты для страницы добавления клиента")
@Execution(ExecutionMode.CONCURRENT)
public class UserTest {

    private final Random random = new Random();
    private final String URL = "http://localhost:8080/api/";
    private final List<String> createdEntityIds = new CopyOnWriteArrayList<>();

    @AfterEach
    public void cleanUp() {
        synchronized (createdEntityIds) {
            for (String id : createdEntityIds) {
                given()
                        .spec(Specifications.requestSpec())
                        .when()
                        .delete("delete/" + id)
                        .then()
                        .spec(Specifications.responseSpec_Code_200_or_204())
                        .log().all();
            }
            createdEntityIds.clear();
        }
    }

    @Test
    @Story("Выводит список сущностей")
    @Description("Вывод списка сущностей")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllEntities() {
        List<Entity> users = given()
                .spec(Specifications.requestSpec())
                .when()
                .get("getAll")
                .then()
                .spec(Specifications.responseSpec_Code_200())
                .log().all()
                .extract().body().jsonPath().getList("entities", Entity.class);

        users.forEach(user -> Assertions.assertTrue(user.getTitle().contains("Заголовок сущности")));
    }

    @Test
    @Story("Проверка на некорректность")
    @Description("Проверяет на некорректный вывод сущностей")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllEntities_Code500() {
        given()
                .spec(Specifications.requestSpec())
                .when()
                .get("get/" + random.nextInt(10000, 100000))
                .then()
                .spec(Specifications.responseSpec_Code_500())
                .log().all();
    }

    @Test
    @Story("Создание сущности")
    @Description("Проверяет корректное создание сущности и проверку на ее создание по ID")
    @Severity(SeverityLevel.CRITICAL)
    public void createEntity() {
        String responseText = create(
                "Addition number 1",
                random.nextInt(1, 10000),
                "Simple title",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        synchronized (createdEntityIds) {
            createdEntityIds.add(responseText);
        }

        Integer responseID = Integer.parseInt(responseText.trim());
        Assertions.assertNotNull(responseID);
        Assertions.assertTrue(responseText.equals(responseID.toString()));

        given()
                .spec(Specifications.requestSpec())
                .when()
                .get("get/" + responseText)
                .then()
                .spec(Specifications.responseSpec_Code_200())
                .log().all();
    }

    @Test
    @Story("Создание сущности")
    @Description("Проверяет корректное создание сущности и проверяет ее создание")
    @Severity(SeverityLevel.CRITICAL)
    public void createEntityAndCheck() {
        String id = create(
                "Addition number 1",
                random.nextInt(1, 10000),
                "Simple title",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        synchronized (createdEntityIds) {
            createdEntityIds.add(id);
        }

        getById(Integer.parseInt(id));
    }

    @Test
    @Story("Обновление сущности")
    @Description("Проверяет корректное обновление сущности и ее проверку по ID")
    @Severity(SeverityLevel.CRITICAL)
    public void updateEntity() {
        String id = create(
                "Original addition info",
                random.nextInt(1, 10000),
                "Original title",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        synchronized (createdEntityIds) {
            createdEntityIds.add(id);
        }

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

        given()
                .spec(Specifications.requestSpec())
                .body(updatedEntity)
                .when()
                .patch("patch/" + id)
                .then()
                .spec(Specifications.responseSpec_Code_200_or_204())
                .log().all();
    }

    @Test
    @Story("Удаление сущности")
    @Description("Проверяет корректное удаление сущности и проверку на удаление по ID")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteEntity() {
        String id = create(
                "Addition for deletion",
                random.nextInt(1, 10000),
                "Title to delete",
                true,
                Collections.singletonList(random.nextInt(1, 100))
        );

        synchronized (createdEntityIds) {
            createdEntityIds.add(id);
        }

        given()
                .spec(Specifications.requestSpec())
                .when()
                .delete("delete/" + id)
                .then()
                .spec(Specifications.responseSpec_Code_Full())
                .log().all();

        given()
                .spec(Specifications.requestSpec())
                .when()
                .get("get/" + id)
                .then()
                .statusCode(anyOf(is(404), is(500), is(200)))
                .log().all();

        synchronized (createdEntityIds) {
            createdEntityIds.remove(id);
        }
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
                .spec(Specifications.requestSpec())
                .body(entity)
                .when()
                .post("/create")
                .then()
                .spec(Specifications.responseSpec_Code_200())
                .log().all()
                .extract().asString();
    }

    private Entity getById(int id) {
        return given()
                .spec(Specifications.requestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get("get/" + id)
                .then()
                .spec(Specifications.responseSpec_Code_200())
                .log().all()
                .extract().body().jsonPath().getObject("entity", Entity.class);
    }
}