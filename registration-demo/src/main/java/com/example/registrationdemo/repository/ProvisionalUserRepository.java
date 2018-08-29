package com.example.registrationdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationdemo.entity.ProvisionalUser;

public interface ProvisionalUserRepository extends JpaRepository<ProvisionalUser, Long> {
    Optional<ProvisionalUser> findByEmail(String email);
    Optional<ProvisionalUser> findByToken(String tokenl);
}
