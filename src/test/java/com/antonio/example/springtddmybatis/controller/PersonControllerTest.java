package com.antonio.example.springtddmybatis.controller;

import com.antonio.example.springtddmybatis.SpringTddMybatisApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PersonControllerTest extends SpringTddMybatisApplicationTests {

    @Test
    void mustFindPersonByDddAndNumberTelephone() {
        given()
                .pathParam("ddd", "86")
                .pathParam("number", "35006330")
        .get("/person/{ddd}/{number}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(3),
                        "name", equalTo("Cauê"),
                        "cpf", equalTo("38767897100"));
    }

    @Test
    void mustReturnErrorNotFoundPersonByTelephoneNonexistent() {
        given()
                .pathParam("ddd","99")
                .pathParam("number", "9898989898")
        .get("/person/{ddd}/{number}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("error", equalTo("Não existe pessoa com o telefone (99)9898989898"));
    }
}
