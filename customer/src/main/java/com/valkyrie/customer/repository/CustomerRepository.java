package com.valkyrie.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{
    @Modifying
    @Transactional
    @Query(value = "update customer set name = :value", nativeQuery = true)
    public int updateNameField(@Param("value") String value);

    @Modifying
    @Transactional
    @Query(value = "update customer set address = :value", nativeQuery = true)
    public int updateAddressField(@Param("value") String value);

    @Modifying
    @Transactional
    @Query(value = "update customer set number = :value", nativeQuery = true)
    public int updateNumberField(@Param("value") Long value);
}
