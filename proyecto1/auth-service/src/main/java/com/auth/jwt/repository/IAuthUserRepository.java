package com.auth.jwt.repository;

import com.auth.jwt.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUserName(String userName);
}