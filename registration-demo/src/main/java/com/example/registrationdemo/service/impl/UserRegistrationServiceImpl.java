package com.example.registrationdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.registrationdemo.repository.UserRepository;
import com.example.registrationdemo.service.UserRegistrationService;

public class UserRegistrationServiceImpl implements UserRegistrationService  {

	private final UserRepository repository;
	
	@Autowired
	UserRegistrationServiceImpl(UserRepository repository) {
		this.repository = repository;
	}
}
