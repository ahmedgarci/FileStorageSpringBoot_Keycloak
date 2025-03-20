package com.example.FileStorageApp.Keycloack;

import java.util.HashMap;
import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.FileStorageApp.Handler.Exceptions.CustomInvalidCredentialsException;
import com.example.FileStorageApp.Requests.Authentication.LoginRequest;
import com.example.FileStorageApp.Requests.Authentication.RegisterRequest;

import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeyCloakService {

    @Value("${keycloak.realm}")
    private String KEYCLOAKREALM;
    @Value("${keycloak.token-url}")
    private String TOKENURL;
    @Value("${keycloak.client-id}")
    private String ClientID;
    @Value("${keycloak.client-secret}")
    private String ClientSecret;
    @Value("${keycloak.users-url}")
    private String usersEndpoint;

    private final KeycloakAdminService KeycloakAdminService;
    private RestTemplate restTemplate = new RestTemplate();

    public void createUser(RegisterRequest request){
        Keycloak keycloak = KeycloakAdminService.authenticateApp();
        UserRepresentation user = new UserRepresentation();
        user.setEmail(request.getEmail());
        user.setEmailVerified(true);
        user.setUsername(request.getFirstname()+ request.getLastname());
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEnabled(true);
        user.setCredentials(List.of(createPassword(request.getPassword())));
        var response = keycloak.realm(KEYCLOAKREALM).users().create(user);
        if(response.getStatus() == HttpStatus.CREATED.value()){
            log.info("user created successfully");
        }else{
            throw new CustomInvalidCredentialsException("user exists with the same email ");            
       }
    }

    public String LogIn(LoginRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userCredentials = new LinkedMultiValueMap<>();
        userCredentials.add("client_id", ClientID);
        userCredentials.add("client_secret", ClientSecret);
        userCredentials.add("grant_type","password");
        userCredentials.add("username",request.getEmail());
        userCredentials.add("password",request.getPassword());
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(userCredentials,headers);
        try {
            ResponseEntity<HashMap> response = restTemplate.exchange(TOKENURL,HttpMethod.POST,httpEntity, HashMap.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return response.getBody().get("access_token").toString();
            }
            
        } catch (HttpClientErrorException e) {
            throw new CustomInvalidCredentialsException("invalid credentials ");        
        }
        return null;
    }

    public void logout(String authorizationHeader){
        String userId = getConnectedUser();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorizationHeader);
        String LOGOUTENDPOINT = String.format("http://localhost:8085/admin/realms/FileStorageAppRealm/users/%s/logout",userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {  
            ResponseEntity<HashMap> response = restTemplate.exchange(LOGOUTENDPOINT,HttpMethod.POST,entity,HashMap.class);
            if(response.getStatusCode().is2xxSuccessful()){
                SecurityContextHolder.clearContext();
                System.out.println("logged out");
            }
        } catch (Exception e) {
            log.error("logout failed : ", e);
        }
    }


    public String getConnectedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!= null && auth.getPrincipal() instanceof Jwt){
            Jwt jwt = (Jwt) auth.getPrincipal();
            String userId = jwt.getClaim("sub");
            return userId;
        }
        return null;
    }

    private CredentialRepresentation createPassword(String password){
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(password);
        credentials.setTemporary(false);
        return credentials;
    }


}
