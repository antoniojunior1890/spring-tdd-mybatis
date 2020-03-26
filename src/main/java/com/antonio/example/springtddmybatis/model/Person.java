package com.antonio.example.springtddmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private List<Address> addresses;
    private List<Telephone> telephones;
}
