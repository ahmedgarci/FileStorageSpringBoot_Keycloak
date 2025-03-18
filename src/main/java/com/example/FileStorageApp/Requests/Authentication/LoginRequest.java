package com.example.FileStorageApp.Requests.Authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "email is required")
    @Email(message = "invalid email ")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8 , message = "password must be at least 8 characters long")
    private String password;
}
