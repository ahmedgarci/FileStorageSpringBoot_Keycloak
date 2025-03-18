package com.example.FileStorageApp.Requests.Files;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UploadFileRequest {
    
    @NotBlank(message = "titile must contain a value")
    private String title;
    @NotEmpty(message = "file is required")
    private MultipartFile file;

}
