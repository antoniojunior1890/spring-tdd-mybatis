package com.antonio.example.springtddmybatis.mapper;


import com.antonio.example.springtddmybatis.mapper.filter.PersonFilter;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.antonio.example.springtddmybatis.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
public class PersonMapperTest {

    private static final String NAME = "Antonio Junior";
    private static final String CPF = "12345678912";
    private static final String DDD = "99";
    private static final String NUMBER = "9999888811";

    private Person person;
    private Telephone telephone;

    @Autowired
    private PersonMapper sut;

    @BeforeEach
    public void setUp() {

        person = new Person();
        person.setName(NAME);
        person.setCpf(CPF);

        telephone = new Telephone();
        telephone.setDdd(DDD);
        telephone.setNumber(NUMBER);
        person.setTelephones(Arrays.asList(telephone));
    }

    @Test
    public void mustFindPersonByCpf() throws Exception {
        Optional<Person> optional = sut.findByCpf("38767897100");

        assertThat(optional.isPresent()).isTrue();

        Person person = optional.get();
        assertThat(person.getId()).isEqualTo(3L);
        assertThat(person.getName()).isEqualTo("Cauê");
        assertThat(person.getCpf()).isEqualTo("38767897100");
    }

    @Test
    public void  mustNotFindPersonCpfNonexistent() throws Exception {
        Optional<Person> optional = sut.findByCpf("111111111");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void mustFindPersonByTelephoneDddAndNumber() throws Exception {
        Optional<Person> optional = sut.findByTelephoneDddAndTelephoneNumber("86", "35006330");

        assertThat(optional.isPresent()).isTrue();

        Person person = optional.get();
        assertThat(person.getId()).isEqualTo(3L);
        assertThat(person.getName()).isEqualTo("Cauê");
        assertThat(person.getCpf()).isEqualTo("38767897100");
    }

    @Test
    void  mustNotFindPersonTelephoneDddAndNumberNonexistent() throws Exception {
        Optional<Person> optional = sut.findByTelephoneDddAndTelephoneNumber("00", "00000000000");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    void mustFilterPersonByNamePart() throws Exception {
        PersonFilter personFilter = new PersonFilter();
        personFilter.setName("a");

        List<Person> personList = sut.findByFilter(personFilter);

        assertThat(personList.size()).isEqualTo(3);
    }

    @Test
    void mustFilterPersonByCpfPart() throws Exception {
        PersonFilter personFilter = new PersonFilter();
        personFilter.setCpf("78");

        List<Person> personList = sut.findByFilter(personFilter);

        assertThat(personList.size()).isEqualTo(3);
    }

    @Test
    void mustFilterPersonByCompositeFilter() throws Exception {
        PersonFilter personFilter = new PersonFilter();
        personFilter.setName("a");
        personFilter.setCpf("78");

        List<Person> personList = sut.findByFilter(personFilter);

        assertThat(personList.size()).isEqualTo(2);
    }

    @Test
    void mustFilterPersonByDddTelephone() throws Exception{
        PersonFilter personFilter = new PersonFilter();
        personFilter.setDdd("21");

        List<Person> personList = sut.findByFilter(personFilter);

        assertThat(personList.size()).isEqualTo(1);
    }

    @Test
    void mustNotFilterPersonByNumberTelephone() throws Exception {
        PersonFilter personFilter = new PersonFilter();
        personFilter.setNumber("000");

        List<Person> personList = sut.findByFilter(personFilter);

        assertThat(personList.size()).isEqualTo(0);
    }

    @Test
    void mustFilterPersonByNumberTelephone() throws Exception {
        PersonFilter personFilter = new PersonFilter();
        personFilter.setNumber("35006330");

        List<Person> personList = sut.findByFilter(personFilter);

        assertThat(personList.size()).isEqualTo(1);

    }

    @Test
    void mustFindPersonById() {

        Optional<Person> optional = sut.findById(3L);

        assertTrue(optional.isPresent());

        Person person = optional.get();
        assertEquals(person.getName(),"Cauê");
        assertEquals(person.getCpf(),"38767897100");
    }

    @Test
    void mustSavePerson() {

        sut.save(person);

        assertNotNull(person.getId());
    }
}

