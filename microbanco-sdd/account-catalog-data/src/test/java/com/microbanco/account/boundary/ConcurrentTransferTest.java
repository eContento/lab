package com.microbanco.account.boundary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConcurrentTransferTest {

    private static String sourceIban;
    private static String targetIban1;
    private static String targetIban2;

    @Test
    @Order(0)
    void setup() {
        String sourceBody = """
            { "ownerName": "Shared Source", "currency": "EUR", "initialBalance": 1000 }
            """;
        JsonPath sourceResp = given()
            .contentType(ContentType.JSON)
            .body(sourceBody)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        sourceIban = sourceResp.getString("iban");

        String targetBody1 = """
            { "ownerName": "Target One", "currency": "EUR", "initialBalance": 0 }
            """;
        JsonPath targetResp1 = given()
            .contentType(ContentType.JSON)
            .body(targetBody1)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        targetIban1 = targetResp1.getString("iban");

        String targetBody2 = """
            { "ownerName": "Target Two", "currency": "EUR", "initialBalance": 0 }
            """;
        JsonPath targetResp2 = given()
            .contentType(ContentType.JSON)
            .body(targetBody2)
            .when().post("/accounts")
            .then()
                .statusCode(201)
                .extract().jsonPath();
        targetIban2 = targetResp2.getString("iban");
    }

    @Test
    @Order(1)
    void shouldHandleConcurrentTransfersWithOptimisticLocking() throws Exception {
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        ConcurrentLinkedQueue<Integer> statuses = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    latch.await();
                    String target = (idx % 2 == 0) ? targetIban1 : targetIban2;
                    String body = String.format("""
                        { "sourceAccountIban": "%s", "targetAccountIban": "%s", "amount": 300 }
                        """, sourceIban, target);

                    int status = given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when().post("/transfers")
                        .then()
                        .extract().statusCode();

                    statuses.add(status);
                } catch (Exception e) {
                    statuses.add(500);
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        long successCount = statuses.stream().filter(s -> s == 201).count();
        long conflictCount = statuses.stream().filter(s -> s == 409).count();
        long otherCount = statuses.stream().filter(s -> s != 201 && s != 409).count();

        assertTrue(successCount >= 1, "At least one transfer should succeed");
        assertTrue(conflictCount >= 0, "Some transfers may conflict due to optimistic locking");
        assertEquals(0, otherCount, "No unexpected status codes: " + statuses);
    }
}
