package com.example.registrationdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationdemo.entity.ProvisionalUser;

public interface ProvisionalUserRepository extends JpaRepository<ProvisionalUser, Long> {
}
