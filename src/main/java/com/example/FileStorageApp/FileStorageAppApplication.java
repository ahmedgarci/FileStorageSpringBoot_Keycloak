package com.example.FileStorageApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.FileStorageApp.Keycloack.KeycloakAdminService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing
public class FileStorageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStorageAppApplication.class, args);
	}
}