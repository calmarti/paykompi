package com.calmarti.paykompi.auth;

import com.calmarti.paykompi.common.BaseApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;



public class AuthApiTest extends BaseApiTest {

    private final String path = "/api/v1/auth/login";

    @Test
    void shouldReturn200AndTokenWhenCredentialsAreValid() {

        String validCredentials = """
                {
                  "username": "admin",
                  "password": "password"
                }
                """;

        given()
                .spec(spec)
                .body(validCredentials)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("token", not(emptyOrNullString()))
                .body("type", equalTo("Bearer"));
    }

    @Test
    void shouldReturn401WhenCredentialsAreInvalid() {

        String invalidCredentials = """
                
                { "username": "wrong_user",
                  "password": "wrong_password"
                }
                """;

        given()
                .spec(spec)
                .body(invalidCredentials)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .contentType(ContentType.JSON)
                .body("message", not(emptyOrNullString()))
                .body("status", equalTo(401))
                .body("path", equalTo(path))
                .body("timestamp", matchesPattern("^\\d{4}-\\d{2}-\\d{2}T.*$"));
    }
}