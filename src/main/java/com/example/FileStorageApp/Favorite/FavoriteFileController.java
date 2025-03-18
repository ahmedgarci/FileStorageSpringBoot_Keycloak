package com.example.FileStorageApp.Favorite;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FileStorageApp.Requests.Favorite.AddToFavoriteRequest;
import com.example.FileStorageApp.Requests.Favorite.DeleteFavFileRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "favorite")
@RequiredArgsConstructor
public class FavoriteFileController {

    private final FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<?> addFileToFavorite(@RequestBody AddToFavoriteRequest request) {
        favoriteService.addToFavorite(request);        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFromFavorite(@RequestBody DeleteFavFileRequest request) {
        favoriteService.removeFavorite(request);        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
        

    

}
