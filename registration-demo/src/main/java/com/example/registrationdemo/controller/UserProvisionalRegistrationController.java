package com.example.registrationdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registrationdemo.entity.ProvisionalUser;
import com.example.registrationdemo.service.UserProvisionalRegistrationService;

@RestController
@RequestMapping(value = "provisional")
public class UserProvisionalRegistrationController {

	private UserProvisionalRegistrationService service;
	
	@Autowired
	UserProvisionalRegistrationController(
			UserProvisionalRegistrationService service
			) {
		this.service = service;
	}
	
	@PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void register(@RequestBody ProvisionalUser user) {
		// TODO 仮登録処理の実装
	}
}
