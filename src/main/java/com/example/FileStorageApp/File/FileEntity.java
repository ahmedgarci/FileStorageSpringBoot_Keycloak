package com.example.FileStorageApp.File;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.FileStorageApp.Common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends BaseEntity {
    
    private String title;

    private String filePath;

    private String fileId;

    private String mimeType;

    private String user;

    

}
