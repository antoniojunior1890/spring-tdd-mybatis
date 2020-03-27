package com.antonio.example.springtddmybatis.service;

import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.service.Exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.service.Exception.OnenessTelephoneException;

public interface PersonService {
    Person save(Person person) throws OnenessCpfException, OnenessTelephoneException;
}
