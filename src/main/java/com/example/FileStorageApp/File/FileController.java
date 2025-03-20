package com.example.FileStorageApp.File;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.FileStorageApp.Requests.Files.DeleteFileRequest;
import com.example.FileStorageApp.Requests.Files.UploadFileRequest;
import com.example.FileStorageApp.Responses.FilePageResponse;
import com.example.FileStorageApp.Responses.FileResponse;

import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("title") String title,@RequestParam("file") MultipartFile file) {
        fileService.uploadFile(title,file);
        return new ResponseEntity<>("uploaded ", HttpStatus.ACCEPTED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestBody DeleteFileRequest request) {
        fileService.deleteFile(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<FilePageResponse<FileResponse>> getConnectedUserFiles(
        @RequestParam(value = "pageNumber" , defaultValue = "0") Integer pageNumber,
        @RequestParam(value = "pageSize" , defaultValue = "10") Integer pageSize
        ) {
        return ResponseEntity.ok(fileService.getMySavedFiles(pageNumber, pageSize));
    }

}