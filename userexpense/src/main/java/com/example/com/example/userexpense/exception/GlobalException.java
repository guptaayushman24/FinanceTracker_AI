package com.example.userexpense.exception;

import com.example.userexpense.dto.ErrorResponsedto;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(com.example.userexpense.exception.HandleRecordException.class)
    public ResponseEntity<ErrorResponsedto> handleExpenseException(HandleRecordException ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandleExpenseNotExist.class)
    public ResponseEntity<ErrorResponsedto> handleExpenseNotExistException(HandleExpenseNotExist ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandleSortExpenseException.class)
    public ResponseEntity<ErrorResponsedto> handleSoerExpenseException(HandleSortExpenseException ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandleExpenseExceptionByMonth.class)
    public ResponseEntity<ErrorResponsedto> handleExpenseExceptionByMonth(HandleExpenseExceptionByMonth ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandleEmptyDataException.class)
    public ResponseEntity<ErrorResponsedto> handleEmptyDataException(HandleEmptyDataException ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandleEmptyStringException.class)
    public ResponseEntity<ErrorResponsedto> handleEmptyDataException(HandleEmptyStringException ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandleInvalidYearException.class)
    public ResponseEntity<ErrorResponsedto> handleInvalidYearExeption(HandleInvalidYearException ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.example.userexpense.exception.HandlePaymentModeException.class)
    public ResponseEntity<ErrorResponsedto> handlePaymentModeException(HandlePaymentModeException ex) {
        ErrorResponsedto error = new ErrorResponsedto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}