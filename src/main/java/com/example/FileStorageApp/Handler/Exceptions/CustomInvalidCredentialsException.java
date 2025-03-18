package com.example.FileStorageApp.Handler.Exceptions;

public class CustomInvalidCredentialsException extends RuntimeException {
    public CustomInvalidCredentialsException(String msg){
        super(msg);
    }
}
