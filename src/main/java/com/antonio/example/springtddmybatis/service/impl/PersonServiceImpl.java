package com.antonio.example.springtddmybatis.service.impl;

import com.antonio.example.springtddmybatis.mapper.PersonMapper;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.service.Exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @Override
    public Person save(Person person) throws OnenessCpfException {
        Optional<Person> optional = personMapper.findByCpf(person.getCpf());

        if(optional.isPresent()) {
            throw new OnenessCpfException();
        }

        return personMapper.save(person);
    }
}
