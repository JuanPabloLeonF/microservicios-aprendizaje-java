package com.mcsv.hotelservice.exception;

import com.mcsv.hotelservice.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceExceptionNotFound.class)
    public ResponseEntity<ResponseModel> handleResourceNotFound(ResourceExceptionNotFound exception) {
        ResponseModel response = new ResponseModel();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceExceptionFieldNotValid.class)
    public ResponseEntity<ResponseModel> handleResourceFieldNotValid(ResourceExceptionFieldNotValid exception) {
        ResponseModel response = new ResponseModel();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
