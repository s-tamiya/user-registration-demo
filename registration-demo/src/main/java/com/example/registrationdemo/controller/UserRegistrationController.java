package com.example.registrationdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registrationdemo.service.UserRegistrationService;

@RestController
@RequestMapping(value = "/register")
public class UserRegistrationController {
	
	private UserRegistrationService service;
	
	@Autowired
	UserRegistrationController(UserRegistrationService service) {
		this.service = service;
	}

	@GetMapping(path = "{token}")
	public void register(@PathVariable(value = "token") String token) {
		// TODO 本登録処理の実装
	}
	
}