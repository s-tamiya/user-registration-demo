package com.example.registrationdemo.service;

import java.util.Optional;

import com.example.registrationdemo.entity.User;

public interface UserService {

    public Optional<User> findUserById(Long id);
    public Optional<User> findUserByEmail(String email);
    public void updateUser(User user);
    public void deleteUser(Long id);
    public void createUser(User user);
}
