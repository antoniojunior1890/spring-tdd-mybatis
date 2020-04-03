package com.antonio.example.springtddmybatis.controller;


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

    @ExceptionHandler(TelephoneNotFoundException.class)
    public ResponseEntity<ApiError> handleTelephoneNotFoundException(TelephoneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    @AllArgsConstructor
    @Getter
    class ApiError {
        private final String error;
    }
}
