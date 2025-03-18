package com.example.FileStorageApp.File;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Integer>  {
    @Query("SELECT f FROM FileEntity f WHERE f.user = :user")
    Page<FileEntity> getUserFiles(String user, Pageable  pageable);
}
