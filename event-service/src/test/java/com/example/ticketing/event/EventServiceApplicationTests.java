package com.example.ticketing.event;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Import(TestcontainersConfiguration.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventServiceApplicationTests {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer serverPort;

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = serverPort;
    }

    @Test
    void createEventReturns201AndValidBody() {
        String jsonPayload = """
                {
                    "name": "Jazz Night",
                    "description": "Good jazz",
                    "price": 122.00
                }
                """;

        given()
                .contentType("application/json")
                .body(jsonPayload)
                .when()
                .post("/api/event")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Jazz Night"))
                .body("description", equalTo("Good jazz"))
                .body("price", equalTo(122.00f));
    }
}
