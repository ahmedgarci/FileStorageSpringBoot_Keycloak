package com.example.FileStorageApp.Favorite;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Integer> {
    
    @Query("""
            SELECT f FROM FavoriteEntity f 
            Where  f.favFile.id = :fileId
            """)
    Optional<FavoriteEntity> findByFavFileId(Integer fileId);

    List<FavoriteEntity> findByUser(String user);


}
