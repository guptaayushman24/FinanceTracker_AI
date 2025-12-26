package com.example.financetrackerai.exception;

public class MissingFieldException extends RuntimeException{
    public MissingFieldException(String messsage){
        super(messsage);
    }
}
