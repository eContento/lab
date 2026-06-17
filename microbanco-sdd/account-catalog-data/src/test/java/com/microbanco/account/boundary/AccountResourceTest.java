package com.microbanco.account.boundary;

import com.microbanco.account.util.IbanUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountResourceTest {

    private static String accountIban;

    @Test
    @Order(1)
    void shouldOpenAccount() {
        String body = """
            {
                "ownerName": "John Doe",
                "currency": "EUR",
                "initialBalance": 1000.00
            }
            """;

        JsonPath response = given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .body("iban", notNullValue())
                .body("ownerName", equalTo("John Doe"))
                .body("currency", equalTo("EUR"))
                .body("balance", equalTo(1000.00f))
                .body("status", equalTo("ACTIVE"))
                .extract().jsonPath();

        accountIban = response.getString("iban");
    }

    @Test
    @Order(2)
    void shouldGetAccount() {
        given()
            .when().get("/accounts/{iban}", accountIban)
            .then()
                .statusCode(200)
                .body("iban", equalTo(accountIban))
                .body("ownerName", equalTo("John Doe"));
    }

    @Test
    @Order(3)
    void shouldListAccounts() {
        given()
            .queryParam("page", 0)
            .queryParam("size", 20)
            .when().get("/accounts")
            .then()
                .statusCode(200)
                .body("items", hasSize(greaterThanOrEqualTo(1)))
                .body("total", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(4)
    void shouldGetBalance() {
        given()
            .when().get("/accounts/{iban}/balance", accountIban)
            .then()
                .statusCode(200)
                .body("accountIban", equalTo(accountIban))
                .body("balance", equalTo(1000.00f))
                .body("currency", equalTo("EUR"));
    }

    @Test
    @Order(5)
    void shouldReturn404ForNonExistentAccount() {
        given()
            .when().get("/accounts/{iban}", IbanUtils.generateIban())
            .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    void shouldReturn400ForInvalidAccountRequest() {
        String body = """
            {
                "ownerName": "",
                "currency": "",
                "initialBalance": -100
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/accounts")
            .then()
                .statusCode(400);
    }

    @Test
    @Order(7)
    void shouldCloseAccount() {
        String createBody = """
            {
                "ownerName": "Jane Doe",
                "currency": "EUR",
                "initialBalance": 0
            }
            """;

        JsonPath targetResponse = given()
            .contentType(ContentType.JSON)
            .body(createBody)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();

        String targetIban = targetResponse.getString("iban");

        String transferBody = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": 1000.00,
                "description": "Drain account for closing"
            }
            """, accountIban, targetIban);

        given()
            .contentType(ContentType.JSON)
            .body(transferBody)
            .when().post("/transfers")
            .then()
                .statusCode(201);

        given()
            .when().delete("/accounts/{iban}", accountIban)
            .then()
                .statusCode(204);

        given()
            .when().get("/accounts/{iban}", accountIban)
            .then()
                .statusCode(200)
                .body("status", equalTo("CLOSED"));
    }

    @Test
    @Order(8)
    void shouldReturn409WhenClosingWithBalance() {
        String body = """
            {
                "ownerName": "Has Balance",
                "currency": "EUR",
                "initialBalance": 500
            }
            """;

        JsonPath response = given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();

        String iban = response.getString("iban");

        given()
            .when().delete("/accounts/{iban}", iban)
            .then()
                .statusCode(409);
    }
}
