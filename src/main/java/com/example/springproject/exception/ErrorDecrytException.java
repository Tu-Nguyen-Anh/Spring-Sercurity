package com.example.springproject.exception;

import com.example.springproject.exception.base.BadRequestException;

public class ErrorDecrytException extends BadRequestException {
  public ErrorDecrytException(){
    setCode("com.example.springproject.exception.ErrorDecrytException");
  }
}
