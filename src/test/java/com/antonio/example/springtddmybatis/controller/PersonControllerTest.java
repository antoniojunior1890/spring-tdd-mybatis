package com.antonio.example.springtddmybatis.controller;

import com.antonio.example.springtddmybatis.SpringTddMybatisApplicationTests;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

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

    @Test
    void mustFindPersonById() {
        given()
                .pathParam("id", "3")
                .get("/person/{id}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Cauê"),
                        "cpf", equalTo("38767897100"));
    }

    @Test
    void mustReturnErrorNotFoundPersonByIdNonexistent() {
        given()
                .pathParam("id", "33")
                .get("/person/{id}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("error", equalTo("Não existe pessoa com ID 33"));
    }

    @Test
    void mustSavePerson() {
        final Person person = new Person();
        person.setName("Lorenzo");
        person.setCpf("62461410720");

        final Telephone telephone = new Telephone();
        telephone.setDdd("79");
        telephone.setNumber("36977168");

        person.setTelephones(Arrays.asList(telephone));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(person)
        .when()
        .post("/person")
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+port+"/person/6"))
                .body("id",equalTo(6),
                        "name",equalTo("Lorenzo"),
                        "cpf",equalTo("62461410720"));

    }
}
