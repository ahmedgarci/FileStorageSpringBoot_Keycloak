package com.example.FileStorageApp.Responses;

public record FileResponse(
    Integer id,String title, String filePath, String mimeType
) {
    
}
