package com.example.userexpense.exception;

public class HandleEmptyDataException extends  RuntimeException{
    public HandleEmptyDataException (String message){
        super(message);
    }
}
