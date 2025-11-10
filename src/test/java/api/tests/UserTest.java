package api.tests;

import api.dto.Addition;
import api.dto.Entity;
import api.dto.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends BaseTest {

    private String loadJsonFromFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/" + filename)));
    }

    @Test
    public void createUser() throws IOException {
        String userData = loadJsonFromFile("req_create.json");

        // Создаем пользователя
        Response response = given(spec)
                .body(userData)
                .post("/create")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        // Проверяем ответ
        Entity createdEntity = response.as(Entity.class);

        assertNotNull(createdEntity.getId());
        assertEquals("Заголовок", createdEntity.getTitle());
        assertTrue(createdEntity.isVerified());
        assertNotNull(createdEntity.getAddition());
        assertEquals("Дополнительная информация", createdEntity.getAddition().getAdditional_info());
        assertEquals(2, createdEntity.getAddition().getAdditional_number());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), createdEntity.getImportant_numbers());

        // Сохраняем ID для очистки
        createdUserIds.add(String.valueOf(createdEntity.getId()));
    }

    @Test
    public void createUserWithDifferentData() throws IOException {
        // Тестируем с другими данными
        String userData = loadJsonFromFile("test.json");

        Response response = given(spec)
                .body(userData)
                .post("/create")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        Entity createdEntity = response.as(Entity.class);

        assertNotNull(createdEntity.getId());
        assertEquals("Заголовок сущности", createdEntity.getTitle());
        assertTrue(createdEntity.isVerified());
        assertEquals("Дополнительные сведения", createdEntity.getAddition().getAdditional_info());
        assertEquals(123, createdEntity.getAddition().getAdditional_number());
        assertEquals(Arrays.asList(42, 87, 15), createdEntity.getImportant_numbers());

        createdUserIds.add(String.valueOf(createdEntity.getId()));
    }

    @Test
    public void getUserById() throws IOException {
        // Сначала создаем пользователя
        String userData = loadJsonFromFile("req_create.json");
        String userId = createUserAndGetId(userData);

        // Получаем пользователя по ID
        Response response = given(spec)
                .get("/get/{id}", userId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Entity entity = response.as(Entity.class);

        assertEquals(Integer.parseInt(userId), entity.getId());
        assertEquals("Заголовок", entity.getTitle());
        assertTrue(entity.isVerified());
    }

    @Test
    public void getUserById_NotFound() {
        // Пытаемся получить несуществующего пользователя
        given(spec)
                .get("/get/{id}", "9999")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getAllUsers() throws IOException {
        // Создаем несколько пользователей для теста
        String userData = loadJsonFromFile("test.json");

        createUserAndGetId(userData);
        createUserAndGetId(userData);

        Response response = given(spec)
                .get("/getAll")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Root root = response.as(Root.class);

        assertNotNull(root.getEntities());
        assertThat(root.getEntities().size(), greaterThanOrEqualTo(2));
        assertTrue(root.getPerPage() > 0);
        assertTrue(root.getPage() >= 0);
    }

    @Test
    public void getAllUsersWithPagination() throws IOException {
        // Тестируем пагинацию с параметрами из файла
        String paginationData = loadJsonFromFile("req_get_all.json");
        JsonNode jsonNode = objectMapper.readTree(paginationData);

        Response response = given(spec)
                .queryParam("perPage", jsonNode.get("perPage").asInt())
                .queryParam("page", jsonNode.get("page").asInt())
                .queryParam("title", jsonNode.get("title").asText())
                .queryParam("verified", jsonNode.get("verified").asBoolean())
                .get("/getAll")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Root root = response.as(Root.class);

        assertNotNull(root.getEntities());
        assertEquals(2, root.getPerPage());
        assertEquals(1, root.getPage());
    }

    @Test
    public void updateUser() throws IOException {
        // Создаем пользователя
        String userData = loadJsonFromFile("req_create.json");
        String userId = createUserAndGetId(userData);

        // Обновляем пользователя
        String updateData = loadJsonFromFile("req_update.json");

        Response response = given(spec)
                .body(updateData)
                .patch("/patch/{id}", userId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Entity updatedEntity = response.as(Entity.class);

        assertEquals(Integer.parseInt(userId), updatedEntity.getId());
        assertEquals("New title3 test", updatedEntity.getTitle());
        assertFalse(updatedEntity.isVerified());
        assertEquals("Дополнительная информация 1111", updatedEntity.getAddition().getAdditional_info());
        assertEquals(2, updatedEntity.getAddition().getAdditional_number());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 10), updatedEntity.getImportant_numbers());
    }

    @Test
    public void updateUser_NotFound() throws IOException {
        // Пытаемся обновить несуществующего пользователя
        String updateData = loadJsonFromFile("req_update.json");

        given(spec)
                .body(updateData)
                .patch("/patch/{id}", "9999")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void updateUserAddition() throws IOException {
        // Создаем пользователя
        String userData = loadJsonFromFile("req_create.json");
        String userId = createUserAndGetId(userData);

        // Обновляем только дополнения с использованием Jackson ObjectNode
        ObjectNode updateData = objectMapper.createObjectNode();
        ObjectNode additionNode = objectMapper.createObjectNode();
        additionNode.put("additional_info", "Обновленная информация");
        additionNode.put("additional_number", 999);
        updateData.set("addition", additionNode);

        Response response = given(spec)
                .body(updateData.toString())
                .patch("/patch/{id}", userId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Entity updatedEntity = response.as(Entity.class);

        assertEquals("Обновленная информация", updatedEntity.getAddition().getAdditional_info());
        assertEquals(999, updatedEntity.getAddition().getAdditional_number());
        // Проверяем, что другие поля не изменились
        assertEquals("Заголовок", updatedEntity.getTitle());
        assertTrue(updatedEntity.isVerified());
    }

    @Test
    public void updateUserWithJacksonObject() throws IOException {
        // Создаем пользователя
        String userData = loadJsonFromFile("req_create.json");
        String userId = createUserAndGetId(userData);

        // Создаем объект для обновления с помощью Jackson
        Entity updateEntity = new Entity();
        updateEntity.setTitle("Обновленный заголовок через Jackson");
        updateEntity.setVerified(false);

        Addition newAddition = new Addition();
        newAddition.setAdditional_info("Новая информация Jackson");
        newAddition.setAdditional_number(777);
        updateEntity.setAddition(newAddition);

        Response response = given(spec)
                .body(updateEntity)
                .patch("/patch/{id}", userId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Entity updatedEntity = response.as(Entity.class);

        assertEquals("Обновленный заголовок через Jackson", updatedEntity.getTitle());
        assertFalse(updatedEntity.isVerified());
        assertEquals("Новая информация Jackson", updatedEntity.getAddition().getAdditional_info());
        assertEquals(777, updatedEntity.getAddition().getAdditional_number());
    }

    @Test
    public void deleteUser() throws IOException {
        // Создаем пользователя
        String userData = loadJsonFromFile("req_create.json");
        String userId = createUserAndGetId(userData);

        // Удаляем пользователя
        given(spec)
                .delete("/delete/{id}", userId)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // Убираем из списка для очистки, так как уже удалили
        createdUserIds.remove(userId);

        // Проверяем, что пользователь действительно удален
        given(spec)
                .get("/get/{id}", userId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deleteUser_NotFound() {
        // Пытаемся удалить несуществующего пользователя
        given(spec)
                .delete("/delete/{id}", "9999")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void createUserWithPartialData() throws IOException {
        // Тест создания с минимальными данными с использованием Jackson
        ObjectNode minimalData = objectMapper.createObjectNode();
        minimalData.put("title", "Минимальный заголовок");
        minimalData.put("verified", false);

        Response response = given(spec)
                .body(minimalData.toString())
                .post("/create")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        Entity createdEntity = response.as(Entity.class);

        assertNotNull(createdEntity.getId());
        assertEquals("Минимальный заголовок", createdEntity.getTitle());
        assertFalse(createdEntity.isVerified());

        createdUserIds.add(String.valueOf(createdEntity.getId()));
    }

    @Test
    public void createUserWithEmptyArrays() throws IOException {
        // Тест создания с пустыми массивами с использованием Jackson
        ObjectNode dataWithEmptyArrays = objectMapper.createObjectNode();
        dataWithEmptyArrays.put("title", "Заголовок с пустыми массивами");
        dataWithEmptyArrays.put("verified", true);
        dataWithEmptyArrays.putArray("important_numbers");

        Response response = given(spec)
                .body(dataWithEmptyArrays.toString())
                .post("/create")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        Entity createdEntity = response.as(Entity.class);

        assertNotNull(createdEntity.getId());
        assertEquals("Заголовок с пустыми массивами", createdEntity.getTitle());
        assertTrue(createdEntity.getImportant_numbers().isEmpty());

        createdUserIds.add(String.valueOf(createdEntity.getId()));
    }

    @Test
    public void verifyResponseStructure() throws IOException {
        // Проверяем структуру ответа при получении списка
        String userData = loadJsonFromFile("req_create.json");
        createUserAndGetId(userData);

        Response response = given(spec)
                .get("/getAll")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        // Проверяем, что ответ соответствует ожидаемой структуре
        response.then()
                .body("entities", notNullValue())
                .body("perPage", greaterThan(0))
                .body("page", greaterThanOrEqualTo(0))
                .body("entities[0].id", notNullValue())
                .body("entities[0].title", notNullValue())
                .body("entities[0].verified", notNullValue());
    }

    @Test
    public void testJsonParsingWithJackson() throws IOException {
        // Демонстрация парсинга JSON с помощью Jackson
        String jsonData = loadJsonFromFile("req_get.json");
        JsonNode jsonNode = objectMapper.readTree(jsonData);

        // Проверяем структуру JSON
        assertTrue(jsonNode.has("entities"));
        assertTrue(jsonNode.has("perPage"));
        assertTrue(jsonNode.has("page"));

        JsonNode entities = jsonNode.get("entities");
        assertTrue(entities.isArray());
        assertEquals(2, entities.size());

        // Проверяем первую сущность
        JsonNode firstEntity = entities.get(0);
        assertEquals(1, firstEntity.get("id").asInt());
        assertEquals("Заголовок", firstEntity.get("title").asText());
        assertTrue(firstEntity.get("verified").asBoolean());
    }

    @Test
    public void createUserFromJacksonParsedData() throws IOException {
        // Парсим JSON файл и используем его для создания пользователя
        String jsonData = loadJsonFromFile("test.json");
        JsonNode jsonNode = objectMapper.readTree(jsonData);

        // Создаем ObjectNode для возможной модификации
        ObjectNode userData = (ObjectNode) jsonNode;
        userData.put("title", "Модифицированный заголовок через Jackson");

        Response response = given(spec)
                .body(userData.toString())
                .post("/create")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        Entity createdEntity = response.as(Entity.class);

        assertNotNull(createdEntity.getId());
        assertEquals("Модифицированный заголовок через Jackson", createdEntity.getTitle());
        assertEquals("Дополнительные сведения", createdEntity.getAddition().getAdditional_info());

        createdUserIds.add(String.valueOf(createdEntity.getId()));
    }
}