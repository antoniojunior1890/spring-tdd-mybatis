package com.antonio.example.springtddmybatis.controller;

import com.antonio.example.springtddmybatis.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(TelephoneNotFoundException.class)
    public ResponseEntity<ApiError> handleTelephoneNotFoundException(TelephoneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ApiError> handlePersonNotFoundException(PersonNotFoundException e) {
        log.error("Requested person not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(OnenessCpfException.class)
    public ResponseEntity<ApiError> handleOnenessCpfException(OnenessCpfException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(OnenessTelephoneException.class)
    public ResponseEntity<ApiError> handleOnenessTelephoneException(OnenessTelephoneException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }
}
