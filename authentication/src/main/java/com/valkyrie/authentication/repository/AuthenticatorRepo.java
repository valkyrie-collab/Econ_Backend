package com.valkyrie.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valkyrie.authentication.model.Authenticator;

@Repository
public interface AuthenticatorRepo extends JpaRepository<Authenticator, String>{

}
