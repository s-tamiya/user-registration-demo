package com.example.registrationdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.registrationdemo.repository.ProvisionalUserRepository;
import com.example.registrationdemo.service.UserProvisionalRegistrationService;

public class UserProvisionalRegistrationServiceImpl implements UserProvisionalRegistrationService {

	private final ProvisionalUserRepository repository;
	
	@Autowired
	UserProvisionalRegistrationServiceImpl(
			ProvisionalUserRepository repository
	) {
		this.repository = repository;
	}
}
