package com.antonio.example.springtddmybatis.service;

import com.antonio.example.springtddmybatis.mapper.PersonMapper;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.service.Exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {

    private static final String NAME = "Antonio Junior";
    private static final String CPF = "12345678912";

    @MockBean
    private PersonMapper personMapper;

    private PersonService sut;

    private Person person;

    @BeforeEach
    public void setUp() throws Exception{

        sut = new PersonServiceImpl(personMapper);

        person = new Person();
        person.setName(NAME);
        person.setCpf(CPF);
    }

    @Test
    public void mustSavePersonInTheRepository() throws Exception {
        sut.save(person);

        verify(personMapper).save(person);
    }

    @Test
    public void shouldNotSaveTwoPeopleWithSameCpf() throws Exception {
        when(personMapper.findByCpf(CPF)).thenReturn(Optional.of(person));

        Assertions.assertThrows(OnenessCpfException.class, () -> {
            sut.save(person);
        });
    }
}

