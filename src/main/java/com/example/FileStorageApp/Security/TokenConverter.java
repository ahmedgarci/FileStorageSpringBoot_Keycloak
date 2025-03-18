package com.example.FileStorageApp.Security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class TokenConverter implements Converter<Jwt,AbstractAuthenticationToken> {
    
    public AbstractAuthenticationToken convert(Jwt token){
        return new JwtAuthenticationToken(token,Stream.concat(new JwtGrantedAuthoritiesConverter().convert(token).stream(), extracAuthorities(token).stream())
        .collect(Collectors.toSet())
        );

    }

    private Collection<? extends GrantedAuthority> extracAuthorities(Jwt jwt){
        var ressources = new HashMap<>(jwt.getClaim("resource_access"));
        var eternal =   (Map<String,List<String>>)(ressources.get("account"));
        var roles =  eternal.get("roles");
        if(eternal.isEmpty() || roles.isEmpty()){
            return List.of();
        }
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.replace("-", "_")))
        .collect(Collectors.toList());

    }


   
}
