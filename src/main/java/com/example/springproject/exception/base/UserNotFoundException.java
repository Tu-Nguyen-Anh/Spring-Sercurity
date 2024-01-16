package com.example.springproject.exception.base;
import static com.example.springproject.constant.ExceptionCode.USER_NOT_FOUND_CODE;

/**
 * UserNotFoundException is a type of exception commonly
 * used to indicate that username cannot be found.
 */
public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(){
        setCode(USER_NOT_FOUND_CODE);
    }
}
