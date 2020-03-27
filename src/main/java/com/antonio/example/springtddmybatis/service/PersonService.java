package com.antonio.example.springtddmybatis.service;

import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.service.Exception.OnenessCpfException;

public interface PersonService {
    Person save(Person person) throws OnenessCpfException;
}
