package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.TodoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static utils.GeneralConstants.BASE_URI_PLACEHOLDER;

@Epic("API jsonplaceholder tests")
@Feature("Negative tests of /todos endpoint")
public class TodoErrorTest extends BaseTest {

    private TodoModel todoRequest = new TodoModel();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI_PLACEHOLDER;
    }

    @Test
    @Story("User/System try to get todo with inexistent Id")
    @Description("GET method for inexistent todo")
    @DisplayName("Get notfound return with inexistent Id")
    public void getTodoWithInexistentIdReturnsNotFound() {
        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .get("/todos/201")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @Story("User/System try to post todo with inexistent Id")
    @Description("POST method for inexistent todo")
    @DisplayName("Post notfound return with inexistent Id")
    public void postTodoWithInexistentIdReturnsNotFound() {
        String requestBody = todoRequest.userId(201).
                title("test").
                completed(true).
                toJSON();

        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/todos/201")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @Story("User/System try to patch todo with query parameter")
    @Description("PATCH method with specific id in query parameter")
    @DisplayName("Patch notfound return with query parameter")
    public void patchTodoWithQueryParamIdReturnsNotFound() {
        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .queryParam("id",1)
                .when()
                .patch("/todos")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @Story("User/System try to put todo with query parameter")
    @Description("PUT method with specific id in query parameter")
    @DisplayName("Put notfound return with query parameter")
    public void putTodoWithoutIdInPathParameterReturnsNotFound() {
        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .put("/todos")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @Story("User/System try to put todo with inexisten id in path parameter")
    @Description("PUT method with inexistent id in path parameter")
    @DisplayName("Put internalServerError return with inexistent path parameter")
    public void putTodoWithInexistentIdInPathParameterReturnsNotFound() {
        given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .put("/todos/201")
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }


}
