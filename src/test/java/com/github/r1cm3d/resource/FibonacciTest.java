package com.github.r1cm3d.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FibonacciTest {

    @Test
    void fib() {
        given()
          .when().get("/fib?n=15")
          .then()
             .statusCode(200)
             .body(is("610"));
    }
}