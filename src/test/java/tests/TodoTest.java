package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.TodoModel;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static utils.GeneralConstants.BASE_URI_PLACEHOLDER;

@Epic("API jsonplaceholder tests")
@Feature("Positive tests of /todos endpoint")
public class TodoTest extends BaseTest {

    private TodoModel todoRequest = new TodoModel();
    private static Logger log = Logger.getAnonymousLogger();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI_PLACEHOLDER;
    }

    @Test
    @Story("User/System try to get all existent existent todos")
    @Description("GET method for all todos")
    @DisplayName("Check all existent todos by Id")
    public void getCountAllTodosByIdSuccessfully() {
        Response response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .get("/todos");

        List<String> resIDs = response.jsonPath().get("id");

        Assertions.assertEquals(resIDs.size(), SC_OK);

    }

    @Test
    @Story("User/System try to get all data related to one specific todo by path parameter")
    @Description("GET method for one specific todo using path parameter")
    @DisplayName("Check a specific todo using path parameter")
    public void getSpecificTodoWithCorrectPathParamIdSuccessfully() {
        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .get("/todos/1")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", equalTo("delectus aut autem"))
                .body("completed", equalTo(false));
    }

    @Test
    @Story("User/System try to get all data related to one specific todo by query parameter")
    @Description("GET method for one specific todo using query parameter")
    @DisplayName("Check a specific todo using query parameter")
    public void getSpecificTodoWithCorrectQueryParamIdSuccessfully() {
        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("id", 1)
                .get("/todos")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .body("userId", hasItem(1))
                .body("id", hasItem(1))
                .body("title", hasItem("delectus aut autem"))
                .body("completed", hasItem(false));
    }

    @Test
    @Story("User/System try to post a specific todo passing data by body")
    @Description("POST method for one specific todo using body data")
    @DisplayName("Post a new todo with body data")
    public void postNewTodoWithCorrectBodySuccessfully() {
        String requestBody = todoRequest.userId(201).
                title("test").
                completed(true).
                toJSON();

        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/todos")
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .contentType(ContentType.JSON)
                .body("userId", equalTo(201))
                .body("id", equalTo(201))
                .body("title", equalTo("test"))
                .body("completed", equalTo(true));
    }

    @Test
    @Story("User/System try to put a specific todo passing data by body")
    @Description("PUT method for one specific todo using body data")
    @DisplayName("Put a new todo with body data")
    public void putExistentTodoWithCompleteBodySuccessfully() {
        String requestBody = todoRequest.userId(1).
                id(1).
                title("test").
                completed(false).
                toJSON();

        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/todos/1")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", equalTo("test"))
                .body("completed", equalTo(false));
    }

    @Test
    @Story("User/System try to patch a specific todo passing data by body")
    @Description("PATCH method for one specific todo using body data")
    @DisplayName("Patch a new todo with body data")
    public void patchExistentTodoWithPartialBodySuccessfully() {
        String requestBody = todoRequest
                .title("test")
                .toJSON();

        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("/todos/1")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .body("title", equalTo("test"));
    }

}
