package com.antonio.example.springtddmybatis.controller;


import com.antonio.example.springtddmybatis.exception.OnenessCpfException;
import com.antonio.example.springtddmybatis.exception.OnenessTelephoneException;
import com.antonio.example.springtddmybatis.exception.PersonNotFoundException;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
import com.antonio.example.springtddmybatis.exception.TelephoneNotFoundException;
import com.antonio.example.springtddmybatis.service.impl.PersonServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonServiceImpl personServiceImpl;

    @Autowired
    public PersonController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping("/{ddd}/{number}")
    public ResponseEntity<Person> findByDddAndNumberTelephone(@PathVariable("ddd") String ddd,
                                                              @PathVariable("number") String number)
                                                                throws TelephoneNotFoundException {

        final Telephone telephone = new Telephone();
        telephone.setDdd(ddd);
        telephone.setNumber(number);

        return ResponseEntity.ok(personServiceImpl.findByTelephone(telephone));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") Long id) throws PersonNotFoundException {

        return ResponseEntity.ok(personServiceImpl.findById(id));
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person, HttpServletResponse response) throws OnenessTelephoneException, OnenessCpfException {
        final Person createdPerson = personServiceImpl.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(createdPerson.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @ExceptionHandler(TelephoneNotFoundException.class)
    public ResponseEntity<ApiError> handleTelephoneNotFoundException(TelephoneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ApiError> handlePersonNotFoundException(PersonNotFoundException e) {
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

    @AllArgsConstructor
    @Getter
    class ApiError {
        private final String error;
    }
}
