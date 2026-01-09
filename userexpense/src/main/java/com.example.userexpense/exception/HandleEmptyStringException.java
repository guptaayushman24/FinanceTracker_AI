package com.example.userexpense.exception;

public class HandleEmptyStringException extends RuntimeException{
    public HandleEmptyStringException (String message){
        super(message);
    }
}
