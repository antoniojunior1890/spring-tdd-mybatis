package com.antonio.example.springtddmybatis.mapper;


import com.antonio.example.springtddmybatis.mapper.filter.PersonFilter;
import com.antonio.example.springtddmybatis.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface PersonMapper {

    Person save(@Param("person") Person person);

    Optional<Person> findByCpf(@Param("cpf") String cpf);

    Optional<Person> findByTelephoneDddAndTelephoneNumber(@Param("ddd") String ddd, @Param("number") String number);

    List<Person> filter(@Param("personFilter") PersonFilter personFilter);
}
