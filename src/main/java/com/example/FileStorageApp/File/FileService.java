package com.example.FileStorageApp.File;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.FileStorageApp.Cloudinary.CloudinaryService;
import com.example.FileStorageApp.Handler.Exceptions.CustomEntityNoFoundException;
import com.example.FileStorageApp.Handler.Exceptions.CustomUnauthorizedError;
import com.example.FileStorageApp.Keycloack.KeyCloakService;
import com.example.FileStorageApp.Requests.Files.DeleteFileRequest;
import com.example.FileStorageApp.Requests.Files.UploadFileRequest;
import com.example.FileStorageApp.Responses.FilePageResponse;
import com.example.FileStorageApp.Responses.FileResponse;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fRepository;
    private final CloudinaryService cService;
    private final FileEntityMapper fMapper;
    private final KeyCloakService keyCloakService;

    public void uploadFile(@NotNull String title,MultipartFile file){
        Map<String,String> uploadedFilesResult =cService.uploadFile(file);
        FileEntity fileEntity = fMapper.toFileEntity(title, uploadedFilesResult);
        String connectedUser = keyCloakService.getConnectedUser();
        if(connectedUser== null){
            throw new CustomUnauthorizedError("u need to login !");
        }
        fileEntity.setUser(connectedUser);
        fileEntity.setMimeType(this.ExtractFileMimeType(file.getOriginalFilename()));
        fRepository.save(fileEntity);
    }

    public void deleteFile(@NotNull DeleteFileRequest request){
        FileEntity savedFile = fRepository.findById(request.getFileId()).orElseThrow(()->new CustomEntityNoFoundException("entity was not found"));
        String connectedUserId = keyCloakService.getConnectedUser();
        if(connectedUserId== null || !(savedFile.getUser().equals(connectedUserId))){
            throw new CustomUnauthorizedError("u are not the owner of that file");
        }
        cService.deleteImage(savedFile.getFileId());
        fRepository.delete(savedFile);
    }

    public FilePageResponse<FileResponse> getMySavedFiles(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<FileEntity> fileEntityPage = fRepository.getUserFiles(keyCloakService.getConnectedUser(), pageable);
        List<FileResponse> fileResponses = fileEntityPage.getContent().stream().map((file)-> new FileResponse(file.getTitle(),file.getFilePath(), file.getMimeType()))
            .collect(Collectors.toList());
        return new FilePageResponse<FileResponse>(fileResponses);
    } 


    private String ExtractFileMimeType(@NotNull String filePath){
        if(filePath.isEmpty()){
            return null;
        }
        Integer lastPointIndex = filePath.lastIndexOf(".");
        if(lastPointIndex.equals(-1)){
            return null;
        }
        return filePath.substring(lastPointIndex+1);
    }


    

    
}
