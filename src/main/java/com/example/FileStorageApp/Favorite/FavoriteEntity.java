package com.example.FileStorageApp.Favorite;

import com.example.FileStorageApp.Common.BaseEntity;
import com.example.FileStorageApp.Common.BaseEntity;
import com.example.FileStorageApp.File.FileEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteEntity extends BaseEntity {

    @NotNull
    private String user;
    
    @ManyToOne
    @JoinColumn(name = "file_id", nullable=false)
    private FileEntity favFile;
    
    
    
}
