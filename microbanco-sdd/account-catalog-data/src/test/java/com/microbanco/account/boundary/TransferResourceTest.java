package com.microbanco.account.boundary;

import com.microbanco.account.util.IbanUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransferResourceTest {

    private static String sourceIban;
    private static String targetIban;

    @Test
    @Order(0)
    void shouldSetupAccounts() {
        String sourceBody = """
            { "ownerName": "Alice", "currency": "EUR", "initialBalance": 1000 }
            """;
        JsonPath sourceResponse = given()
            .contentType(ContentType.JSON)
            .body(sourceBody)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        sourceIban = sourceResponse.getString("iban");

        String targetBody = """
            { "ownerName": "Bob", "currency": "EUR", "initialBalance": 500 }
            """;
        JsonPath targetResponse = given()
            .contentType(ContentType.JSON)
            .body(targetBody)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        targetIban = targetResponse.getString("iban");
    }

    @Test
    @Order(1)
    void shouldExecuteTransfer() {
        String body = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": 300.00,
                "description": "Test payment"
            }
            """, sourceIban, targetIban);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/transfers")
            .then()
                .statusCode(201)
                .body("sourceAccountIban", equalTo(sourceIban))
                .body("targetAccountIban", equalTo(targetIban))
                .body("amount", equalTo(300.00f))
                .body("description", equalTo("Test payment"));
    }

    @Test
    @Order(2)
    void shouldReturnUpdatedBalancesAfterTransfer() {
        given()
            .when().get("/accounts/{iban}/balance", sourceIban)
            .then()
                .statusCode(200)
                .body("balance", equalTo(700.00f));

        given()
            .when().get("/accounts/{iban}/balance", targetIban)
            .then()
                .statusCode(200)
                .body("balance", equalTo(800.00f));
    }

    @Test
    @Order(3)
    void shouldShowTransferInHistory() {
        given()
            .queryParam("page", 0)
            .queryParam("size", 20)
            .when().get("/accounts/{iban}/transfers", sourceIban)
            .then()
                .statusCode(200)
                .body("items", hasSize(greaterThanOrEqualTo(1)))
                .body("total", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(4)
    void shouldRejectTransferToSameAccount() {
        String body = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": 100.00
            }
            """, sourceIban, sourceIban);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/transfers")
            .then()
                .statusCode(400);
    }

    @Test
    @Order(5)
    void shouldRejectTransferWithInsufficientBalance() {
        String body = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": 10000.00
            }
            """, sourceIban, targetIban);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/transfers")
            .then()
                .statusCode(400);
    }

    @Test
    @Order(6)
    void shouldRejectTransferWithNegativeAmount() {
        String body = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": -100.00
            }
            """, sourceIban, targetIban);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/transfers")
            .then()
                .statusCode(400);
    }

    @Test
    @Order(7)
    void shouldRejectTransferFromNonExistentAccount() {
        String body = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": 100.00
            }
            """, IbanUtils.generateIban(), targetIban);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/transfers")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void shouldRejectTransferToNonExistentAccount() {
        String body = String.format("""
            {
                "sourceAccountIban": "%s",
                "targetAccountIban": "%s",
                "amount": 100.00
            }
            """, sourceIban, IbanUtils.generateIban());

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/transfers")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    void shouldRejectTransferFromClosedAccount() {
        String body = """
            { "ownerName": "Temp", "currency": "EUR", "initialBalance": 100 }
            """;
        JsonPath tempResponse = given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        String tempIban = tempResponse.getString("iban");

        String tempTarget = """
            { "ownerName": "TempTarget", "currency": "EUR", "initialBalance": 0 }
            """;
        JsonPath tempTargetResponse = given()
            .contentType(ContentType.JSON)
            .body(tempTarget)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        String tempTargetIban = tempTargetResponse.getString("iban");

        String drainBody = String.format("""
            { "sourceAccountIban": "%s", "targetAccountIban": "%s", "amount": 100 }
            """, tempIban, tempTargetIban);
        given()
            .contentType(ContentType.JSON)
            .body(drainBody)
            .when().post("/transfers")
            .then()
                .statusCode(201);

        given()
            .when().delete("/accounts/{iban}", tempIban)
            .then()
                .statusCode(204);

        String transferFromClosed = String.format("""
            { "sourceAccountIban": "%s", "targetAccountIban": "%s", "amount": 10 }
            """, tempIban, targetIban);
        given()
            .contentType(ContentType.JSON)
            .body(transferFromClosed)
            .when().post("/transfers")
            .then()
                .statusCode(409);
    }
}
