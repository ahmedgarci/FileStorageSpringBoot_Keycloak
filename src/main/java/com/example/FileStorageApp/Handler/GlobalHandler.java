package com.example.FileStorageApp.Handler;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.FileStorageApp.Handler.Exceptions.CustomEntityNoFoundException;
import com.example.FileStorageApp.Handler.Exceptions.CustomInvalidCredentialsException;
import com.example.FileStorageApp.Handler.Exceptions.CustomUnauthorizedError;
import com.example.FileStorageApp.Handler.Exceptions.FileUploadError;
import com.example.FileStorageApp.Responses.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(FileUploadError.class)
    public ResponseEntity<ErrorResponse> handleFileUploadError(FileUploadError fileUploadError){
        ErrorResponse error = new ErrorResponse(fileUploadError.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomUnauthorizedError.class)
    public ResponseEntity<ErrorResponse> handleFileUploadError(CustomUnauthorizedError unauthorizedError){
        ErrorResponse error = new ErrorResponse(unauthorizedError.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String,String> handleFieldValidation(MethodArgumentNotValidException fieldErrors){
        HashMap<String,String> erros = new HashMap<>();
        for (FieldError error : fieldErrors.getBindingResult().getFieldErrors()) {
            erros.put(error.getField(),error.getDefaultMessage());            
        }
        return erros;
    }    

    @ExceptionHandler(CustomEntityNoFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse hanldeEntityNotFoundException(EntityNotFoundException exception){
        return new ErrorResponse(exception.getMessage(),HttpStatus.NOT_FOUND.value());
    }


    @ExceptionHandler(CustomInvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(CustomInvalidCredentialsException exception){    
        ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);    
    }


}