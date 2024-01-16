package com.example.springproject.exception;

import com.example.springproject.exception.base.BadRequestException;

import static com.example.springproject.constant.ExceptionCode.INVALID_BRITH_CODE;

public class InvalidDateOfBirthException extends BadRequestException {
    public InvalidDateOfBirthException(){
        setCode(INVALID_BRITH_CODE);
    }
}