package com.example.FileStorageApp.Keycloack;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {
    
    @Value("${keycloak.client-id}")
    private String ClientID;
    @Value("${keycloak.client-secret}")
    private String ClientSecret;
    @Value("${keycloak.realm}")
    private String KEYCLOAKREALM;
    @Value("${keycloak.url}")
    private String KEYCLOAKURL;
    

    public Keycloak authenticateApp(){
        return KeycloakBuilder.builder()
            .clientId(ClientID)
            .clientSecret(ClientSecret)
            .realm(KEYCLOAKREALM)
            .serverUrl(KEYCLOAKURL)
            .grantType("client_credentials")
            .build();
    }

    public String getAdminToken(){
        try {
            AccessTokenResponse response = authenticateApp().tokenManager().getAccessToken();
            System.out.println(response);
            return response.getToken();
        } catch (Exception e) {
            throw new RuntimeException("error occured while retrieving the admin token from keycloak");
        }
   

    }


}
