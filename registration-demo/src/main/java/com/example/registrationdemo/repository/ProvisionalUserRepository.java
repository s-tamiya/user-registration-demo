package com.example.registrationdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationdemo.entity.ProvisionalUser;
import com.example.registrationdemo.entity.User;

public interface ProvisionalUserRepository extends JpaRepository<ProvisionalUser, Long> {
	Optional<User> findByEmail(String email);
}
