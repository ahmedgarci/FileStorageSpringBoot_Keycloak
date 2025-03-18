package com.example.FileStorageApp.Cloudinary;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.FileStorageApp.Handler.Exceptions.FileUploadError;

import jakarta.validation.constraints.NotNull;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String,String> uploadFile(@NotNull MultipartFile file){
        try {
            Map cloudinaryResponse =cloudinary.uploader().upload(file.getBytes(),ObjectUtils.asMap("resource_type", "auto"));
            Map<String,String> result= new HashMap<>();
            result.put("secure-url", cloudinaryResponse.get("secure_url").toString());
            System.out.println(cloudinaryResponse);
            result.put("public_id", cloudinaryResponse.get("public_id").toString());
            return result;
        } catch (Exception e) {
            throw new FileUploadError("error occured while uploading the file : "+e.getMessage());
        }
    }

    public void deleteImage(String imageId){
        try {
            cloudinary.uploader().destroy(imageId, null);
        } catch (Exception e) {
            throw new FileUploadError("error occured while uploading the file");
        }
    }
}
