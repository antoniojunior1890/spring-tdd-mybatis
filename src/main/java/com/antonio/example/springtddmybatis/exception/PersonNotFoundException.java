package com.antonio.example.springtddmybatis.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
