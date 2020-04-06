package com.antonio.example.springtddmybatis.controller;


import com.antonio.example.springtddmybatis.exception.*;
import com.antonio.example.springtddmybatis.mapper.filter.PersonFilter;
import com.antonio.example.springtddmybatis.model.Person;
import com.antonio.example.springtddmybatis.model.Telephone;
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
import java.util.List;

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

        Person person = personServiceImpl.findById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person, HttpServletResponse response) throws OnenessTelephoneException, OnenessCpfException {
        final Person createdPerson = personServiceImpl.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(createdPerson.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Person>> findByFilter(@RequestBody PersonFilter personFilter) {
        return ResponseEntity.ok(personServiceImpl.findByFilter(personFilter));
    }

}
