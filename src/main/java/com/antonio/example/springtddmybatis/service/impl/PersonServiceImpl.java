package com.antonio.example.springtddmybatis.service.impl;

import com.antonio.example.springtddmybatis.exception.PersonNotFoundException;
import com.antonio.example.springtddmybatis.mapper.PersonMapper;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.antonio.example.springtddmybatis.exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.exception.OnenessTelephoneException;
import com.antonio.example.springtddmybatis.exception.TelephoneNotFoundException;
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
    public Person save(Person person) throws OnenessCpfException, OnenessTelephoneException {
        Optional<Person> optional = personMapper.findByCpf(person.getCpf());

        if(optional.isPresent()) {
            throw new OnenessCpfException();
        }

        final String ddd = person.getTelephones().get(0).getDdd();
        final String number = person.getTelephones().get(0).getNumber();

        optional = personMapper.findByTelephoneDddAndTelephoneNumber(ddd, number);

        if (optional.isPresent()) {
            throw new OnenessTelephoneException();
        }

        personMapper.save(person);

        return person;
    }

    @Override
    public Person findByTelephone(Telephone telephone) throws TelephoneNotFoundException {
        return personMapper.findByTelephoneDddAndTelephoneNumber(telephone.getDdd(), telephone.getNumber()).orElseThrow(() -> new TelephoneNotFoundException("Não existe pessoa com o telefone ("+telephone.getDdd()+")"+telephone.getNumber()));
    }

    @Override
    public Person findById(Long id) throws PersonNotFoundException {
        return personMapper.findById(id).orElseThrow(() -> new PersonNotFoundException("Não existe pessoa com o id "+id));
    }
}
