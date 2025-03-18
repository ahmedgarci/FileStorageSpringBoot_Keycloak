package com.example.FileStorageApp.Cloudinary;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;



@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = ObjectUtils.asMap(
            "cloud_name", "#",
            "api_key", "#",
            "api_secret", "#");
    return new Cloudinary(config);
    
    }


}
