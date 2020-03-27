package com.antonio.example.springtddmybatis.mapper;


import com.antonio.example.springtddmybatis.model.Person;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
public class PersonMapperTest {

    @Autowired
    private PersonMapper sut;

    @Test
    public void mustSearchPersonByCpf() throws Exception {
        Optional<Person> optional = sut.findByCpf("38767897100");

        assertThat(optional.isPresent()).isTrue();

        Person person = optional.get();
        assertThat(person.getId()).isEqualTo(3L);
        assertThat(person.getName()).isEqualTo("Cauê");
        assertThat(person.getCpf()).isEqualTo("38767897100");
    }
}

