package com.example.FileStorageApp.Handler.Exceptions;

public class CustomUnauthorizedError extends RuntimeException {
 
    public CustomUnauthorizedError(String msg){
        super(msg);
    }
}
