package com.antonio.example.springtddmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Telephone implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String ddd;
    private String number;
}
