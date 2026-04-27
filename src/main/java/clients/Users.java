package clients;

import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Users {
    public static ValidatableResponse getUsers(Map<String, Object> params) {
        return given()
                .queryParams(params)
                .when()
                .get("/users")
                .then();
    }
}
