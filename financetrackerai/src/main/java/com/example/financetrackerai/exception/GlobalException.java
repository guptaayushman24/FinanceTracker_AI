package com.example.financetrackerai.exception;

import com.example.userexpense.dto.ErrorResponsedto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(com.example.financetrackerai.exception.UserNotFoundException.class)
    public ResponseEntity<ErrorResponsedto> handleUserNotFound(UserNotFoundException ex){
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.example.financetrackerai.exception.MissingFieldException.class)
    public ResponseEntity<ErrorResponsedto> handleMissingFieldException(MissingFieldException ex){
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
