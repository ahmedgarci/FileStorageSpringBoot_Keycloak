package com.example.FileStorageApp.Handler.Exceptions;

public class CustomEntityNoFoundException extends RuntimeException {
    
    public CustomEntityNoFoundException(String msg){
        super(msg);
    }
}
