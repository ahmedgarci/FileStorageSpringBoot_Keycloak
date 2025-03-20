package com.example.FileStorageApp.Favorite;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.FileStorageApp.File.FileEntity;
import com.example.FileStorageApp.File.FileRepository;
import com.example.FileStorageApp.Handler.Exceptions.CustomEntityNoFoundException;
import com.example.FileStorageApp.Handler.Exceptions.CustomUnauthorizedError;
import com.example.FileStorageApp.Keycloack.KeyCloakService;
import com.example.FileStorageApp.Requests.Favorite.AddToFavoriteRequest;
import com.example.FileStorageApp.Requests.Favorite.DeleteFavFileRequest;
import com.example.FileStorageApp.Responses.FilePageResponse;
import com.example.FileStorageApp.Responses.FileResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    
    private final FavoriteRepository favRepository;
    private final FileRepository fileRepository;
    private final KeyCloakService kService;


    public void addToFavorite(AddToFavoriteRequest request){
        String userId = kService.getConnectedUser();
        if(userId == null){
            throw new CustomUnauthorizedError("user not logged in");
        }
        FileEntity fileEntity= fileRepository.findById(request.getFileId()).orElseThrow(()-> new CustomEntityNoFoundException("file entity not found"));
        favRepository.save(new FavoriteEntity().builder().user(userId).favFile(fileEntity).build());       
    }

    public void removeFavorite(DeleteFavFileRequest request){
        String userId = kService.getConnectedUser();
        if(userId == null){
            throw new CustomUnauthorizedError("user not logged in");
        }
        FavoriteEntity savedFavEntity = favRepository.findByFavFileId(request.getFileId())
            .orElseThrow(()-> new CustomEntityNoFoundException("cant find fav entity with file id :"+request.getFileId()));

        favRepository.delete(savedFavEntity);
    }
    
    public FilePageResponse<FileResponse> getMyFavoriteFiles(){
        String userId = kService.getConnectedUser();
        if(userId == null){
            throw new CustomUnauthorizedError("user not logged in");
        }
        List<FavoriteEntity> userFavoriteFiles =  favRepository.findByUser(userId)  ;
        List<FileResponse> response = userFavoriteFiles.stream().map((element)->new FileResponse(element.getId(),element.getFavFile().getTitle(),element.getFavFile().getFilePath(),element.getFavFile().getMimeType()))
        .collect(Collectors.toList());
        return new FilePageResponse<>(response);
    }
    




}