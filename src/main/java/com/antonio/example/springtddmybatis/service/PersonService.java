package com.antonio.example.springtddmybatis.service;

import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.antonio.example.springtddmybatis.service.Exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.service.Exception.OnenessTelephoneException;
import com.antonio.example.springtddmybatis.service.Exception.TelephoneNotFoundException;

public interface PersonService {
    Person save(Person person) throws OnenessCpfException, OnenessTelephoneException;

    Person findByTelephone(Telephone telephone) throws TelephoneNotFoundException;
}
