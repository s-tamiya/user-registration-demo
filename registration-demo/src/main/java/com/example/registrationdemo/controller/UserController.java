package com.example.registrationdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registrationdemo.entity.User;
import com.example.registrationdemo.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> get(@PathVariable(value = "id") Long id) {
        return null;
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> update() {
        return null;
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String remove() {
        return "success";
    }

    @PostMapping(
            path = "preentry",
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String provisionalRgister(@RequestBody User user) {
        return null;
    }

    @GetMapping(
            path = "auth/{token}",
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String register(@PathVariable(value = "token") String token) {
        return null;
    }
}
