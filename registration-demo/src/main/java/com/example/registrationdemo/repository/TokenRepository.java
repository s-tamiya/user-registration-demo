package com.example.registrationdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationdemo.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
