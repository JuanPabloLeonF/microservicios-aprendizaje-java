package com.msvc.userservice.exception;

import com.msvc.userservice.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceExceptionNotFound.class)
    public ResponseEntity<ResponseModel> handlerResourceExceptionNotFound(ResourceExceptionNotFound exception) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(exception.getMessage());
        responseModel.setStatus(HttpStatus.NOT_FOUND);
        responseModel.setCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceExceptionFieldNotValid.class)
    public ResponseEntity<ResponseModel> handlerResourceExceptionFieldNotValid(ResourceExceptionFieldNotValid exception) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(exception.getMessage());
        responseModel.setStatus(HttpStatus.BAD_REQUEST);
        responseModel.setCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }
}
