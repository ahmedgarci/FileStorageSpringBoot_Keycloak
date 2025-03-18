package com.example.FileStorageApp.Keycloack;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FileStorageApp.Requests.Authentication.LoginRequest;
import com.example.FileStorageApp.Requests.Authentication.RegisterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "authentication")
@RequiredArgsConstructor
public class AuthController {
    private final KeyCloakService keyCloakService;

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@Valid @RequestBody RegisterRequest request) {
        keyCloakService.createUser(request);   
        return new ResponseEntity<>("user registred ",HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        return new ResponseEntity<>(keyCloakService.LogIn(request),HttpStatus.OK);
    }
    
    

}