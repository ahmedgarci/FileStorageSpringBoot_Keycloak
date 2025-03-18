package com.example.FileStorageApp.Handler.Exceptions;

public class FileUploadError extends RuntimeException {
    
    public FileUploadError(String msg){
        super(msg);
    }
    
}
