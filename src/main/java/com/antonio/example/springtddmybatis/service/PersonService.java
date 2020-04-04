package com.antonio.example.springtddmybatis.service;

import com.antonio.example.springtddmybatis.exception.PersonNotFoundException;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.antonio.example.springtddmybatis.exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.exception.OnenessTelephoneException;
import com.antonio.example.springtddmybatis.exception.TelephoneNotFoundException;

public interface PersonService {
    Person save(Person person) throws OnenessCpfException, OnenessTelephoneException;

    Person findByTelephone(Telephone telephone) throws TelephoneNotFoundException;

    Person findById(Long id) throws PersonNotFoundException;
}
