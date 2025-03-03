package com.belong.customer.phoneservice.exception;

import com.belong.customer.phoneservice.model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(PhoneNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleIPhoneNotFoundException(PhoneNotFoundException ex) {
        ErrorResponseModel error = new ErrorResponseModel(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
