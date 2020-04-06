package com.antonio.example.springtddmybatis.controller;

import com.antonio.example.springtddmybatis.SpringTddMybatisApplicationTests;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PersonControllerTest extends SpringTddMybatisApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mustFindPersonByDddAndNumberTelephoneMockMvc() throws Exception {
        mockMvc.perform(get("/person/{ddd}/{number}","86", "35006330")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Cauê"))
                .andExpect(jsonPath("$.cpf").value("38767897100"));
    }

//    @Test
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
    void mustReturnErrorNotFoundPersonByTelephoneNonexistentMockMvc() throws Exception {
        mockMvc.perform(get("/person/{ddd}/{number}","99", "9898989898")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Não existe pessoa com o telefone (99)9898989898"));
    }

//    @Test
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
    void mustFindPersonByIdMockMvc() throws Exception {
        mockMvc.perform(get("/person/{id}","3")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cauê"))
                .andExpect(jsonPath("$.cpf").value("38767897100"));
    }

//    @Test
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
    void mustReturnErrorNotFoundPersonByIdNonexistentMockMvc() throws Exception {
        mockMvc.perform(get("/person/{id}","33")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Não existe pessoa com ID 33"));
    }

//    @Test
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
    void mustSavePersonMockMvc() throws Exception {

        final Person person = new Person();
        person.setName("Lorenzo");
        person.setCpf("62461410720");

        final Telephone telephone = new Telephone();
        telephone.setDdd("79");
        telephone.setNumber("36977168");

        person.setTelephones(Arrays.asList(telephone));

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(person)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/person/6"))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Lorenzo"))
                .andExpect(jsonPath("$.cpf").value("62461410720"));
    }

//    @Test
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

    @Test
    void mustNotSaveTwoPeopleWithSameCpf() throws Exception {
        final Person person = new Person();
        person.setName("Lorenzo");
        person.setCpf("72788740417");

        final Telephone telephone = new Telephone();
        telephone.setDdd("79");
        telephone.setNumber("36977168");

        person.setTelephones(Arrays.asList(telephone));

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(person)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Já existe pessoa cadastrada com o cpf 72788740417"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
