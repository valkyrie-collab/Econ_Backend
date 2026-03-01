package com.valkyrie.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.seller.model.Image;


public interface SellerImageRepository extends JpaRepository<Image, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from image where username = :username", nativeQuery = true)
    public int removeSellerDp(@Param("username") String username);

    @Query(value = "select * from image where username = :username", nativeQuery = true)
    public Image findByUsername(@Param("username") String username);

    @Query(value = "select exists (select 1 from image where username = :username)", nativeQuery = true)
    public boolean isExistByUsername(@Param("username") String username);
}
