package com.example.userexpense.exception;

import com.example.userexpense.dto.ErrorResponsedto;
import org.springframework.http.ResponseEntity;

public class HandleExpenseNotExist extends RuntimeException{
    public  HandleExpenseNotExist (String message){
        super(message);
    }
}
