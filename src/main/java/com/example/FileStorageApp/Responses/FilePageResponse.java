package com.example.FileStorageApp.Responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FilePageResponse<T> {

    List<T> content;


    
    
}
