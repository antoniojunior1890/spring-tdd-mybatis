package com.antonio.example.springtddmybatis.service;

import com.antonio.example.springtddmybatis.exception.PersonNotFoundException;
import com.antonio.example.springtddmybatis.mapper.PersonMapper;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.antonio.example.springtddmybatis.exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.exception.OnenessTelephoneException;
import com.antonio.example.springtddmybatis.exception.TelephoneNotFoundException;
import com.antonio.example.springtddmybatis.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Antonio Junior";
    private static final String CPF = "12345678912";
    private static final String DDD = "99";
    private static final String NUMBER = "9999888811";

    @MockBean
    private PersonMapper personMapper;

    private PersonService sut;

    private Person person;
    private Telephone telephone;

    @BeforeEach
    public void setUp() throws Exception{

        sut = new PersonServiceImpl(personMapper);

        person = new Person();
        person.setId(ID);
        person.setName(NAME);
        person.setCpf(CPF);

        telephone = new Telephone();
        telephone.setDdd(DDD);
        telephone.setNumber(NUMBER);
        person.setTelephones(Arrays.asList(telephone));
    }

    @Test
    public void mustSavePersonInTheRepository() throws Exception {
        sut.save(person);

        verify(personMapper).save(person);
    }

    @Test
    public void mustNotSaveTwoPeopleWithSameCpf() throws Exception {
        when(personMapper.findByCpf(CPF)).thenReturn(Optional.of(person));

        assertThrows(OnenessCpfException.class, () -> {
            sut.save(person);
        });
    }

    @Test
    public void mustNotSaveTwoPeopleWithSamePhone() throws Exception {
        when(personMapper.findByTelephoneDddAndTelephoneNumber(DDD, NUMBER)).thenReturn(Optional.of(person));

        assertThrows(OnenessTelephoneException.class, () -> {
            sut.save(person);
        });
    }

    @Test
    public void mustReturnExceptionNotFoundWhenThereNoPersonWithDddAndTelephoneNumber () throws Exception {
        assertThrows(TelephoneNotFoundException.class, () -> {
            sut.findByTelephone(telephone);
        });
    }

    @Test
    void mustReturnDataTelephoneInException() {
        Exception exception = assertThrows( TelephoneNotFoundException.class, () -> sut.findByTelephone(telephone));

        assertEquals(exception.getMessage(), "Não existe pessoa com o telefone ("+DDD+")"+NUMBER);

    }

    @Test
    public void mustFindByTelephoneDddAndNumber () throws Exception {
        when(personMapper.findByTelephoneDddAndTelephoneNumber(DDD, NUMBER)).thenReturn(Optional.of(person));

        Person personTest = sut.findByTelephone(telephone);

        verify(personMapper).findByTelephoneDddAndTelephoneNumber(DDD,NUMBER);

        assertThat(personTest).isNotNull();
        assertThat(personTest.getName()).isEqualTo(NAME);
        assertThat(personTest.getCpf()).isEqualTo(CPF);

    }

    @Test
    void mustFindPersonById() throws Exception {
        when(personMapper.findById(ID)).thenReturn(Optional.of(person));

        sut.findById(ID);
        verify(personMapper).findById(ID);
    }

    @Test
    void mustReturnPersonNotFoundException()  {
        assertThrows(PersonNotFoundException.class, () -> {
            sut.findById(ID);
        });
    }
}

