package com.example.springproject.exception;

import com.example.springproject.exception.base.BadRequestException;

public class ErrorEncrytException extends BadRequestException {
  public ErrorEncrytException(){
    setCode("com.example.springproject.exception.ErrorEncrytException");
  }
}
