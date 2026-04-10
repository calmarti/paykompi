package com.calmarti.paykompi.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }


    @Test
    void shouldReturn200AndTokenWhenCredentialsAreValid() {

        String requestBody = """
                {
                  "username": "admin",
                  "password": "password"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/v1/auth/login")
        .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("type", equalTo("Bearer"));


    }

}