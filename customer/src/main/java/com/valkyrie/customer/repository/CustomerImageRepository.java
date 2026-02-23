package com.valkyrie.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.customer.model.Image;

public interface CustomerImageRepository extends JpaRepository<Image, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from image where username = :username", nativeQuery = true)
    public int removeCustomerDp(@Param("username") String username);

    @Query(value = "select * from image where username = :username", nativeQuery = true)
    public Image findByUsername(@Param("username") String username);

    @Query(value = "select exists (select 1 from image where username = :username)", nativeQuery = true)
    public boolean isExistByUsername(@Param("username") String username);
}
