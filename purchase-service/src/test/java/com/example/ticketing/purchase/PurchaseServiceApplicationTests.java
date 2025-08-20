package com.example.ticketing.purchase;

import com.example.ticketing.purchase.configuration.KafkaMockConfig;
import com.example.ticketing.purchase.wiremock.StockClientWiremock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;


import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {KafkaMockConfig.class})
@AutoConfigureWireMock(port = 0)
public class PurchaseServiceApplicationTests {

    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0");

    static {
        mySQLContainer.start();
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldSubmitPurchase_whenTicketIsAvailable() {
        var requestJson = """
                    {
                        "stockCode": "pezet",
                        "price": 1000,
                        "quantity": 1,
                        "userInfo": {
                            "email": "test@example.com"
                        }
                    }
                """;

        StockClientWiremock.stubStockAvailable("pezet", 1);

        given()
                .contentType("application/json")
                .body(requestJson)
                .when()
                .post("/api/purchase")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldFailPurchase_whenTicketIsNotAvailable() {
        var requestJson = """
                    {
                        "stockCode": "pezet",
                        "price": 1000,
                        "quantity": 121212
                    }
                """;

        StockClientWiremock.stubStockUnavailable("pezet", 121212);

        given()
                .contentType("application/json")
                .body(requestJson)
                .when()
                .post("/api/purchase")
                .then()
                .statusCode(500);
    }

}
