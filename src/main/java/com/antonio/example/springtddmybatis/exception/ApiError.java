package com.antonio.example.springtddmybatis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String error;
}
