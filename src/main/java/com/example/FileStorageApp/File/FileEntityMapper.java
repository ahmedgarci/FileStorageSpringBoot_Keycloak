package com.example.FileStorageApp.File;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FileStorageApp.Requests.Files.UploadFileRequest;
@Service
public class FileEntityMapper {
    
//    public FileEntity toFileEntity(UploadFileRequest request,Map<String,String> uploadedFileData){
        public FileEntity toFileEntity(String title ,Map<String,String> uploadedFileData){
        return FileEntity.builder().filePath(uploadedFileData.get("secure-url")).title(title).fileId(uploadedFileData.get("public_id"))
        .build();

    }
}
