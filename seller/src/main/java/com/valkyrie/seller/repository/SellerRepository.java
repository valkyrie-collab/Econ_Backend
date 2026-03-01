package com.valkyrie.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.seller.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String>{
    @Modifying
    @Transactional
    @Query(value = "update seller set name = :value", nativeQuery = true)
    public int updateNameField(@Param("value") String value);

    @Modifying
    @Transactional
    @Query(value = "update seller set address = :value", nativeQuery = true)
    public int updateAddressField(@Param("value") String value);

    @Modifying
    @Transactional
    @Query(value = "update seller set number = :value", nativeQuery = true)
    public int updateNumberField(@Param("value") Long value);
}
