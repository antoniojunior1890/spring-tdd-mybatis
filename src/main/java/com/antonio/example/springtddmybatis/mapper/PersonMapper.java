package com.antonio.example.springtddmybatis.mapper;


import com.antonio.example.springtddmybatis.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public class PersonMapper {
    public Person save(Person person) {
        return new Person();
    }
}
