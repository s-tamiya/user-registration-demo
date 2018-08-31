package com.example.registrationdemo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.registrationdemo.entity.User;
import com.example.registrationdemo.repository.UserRepository;
import com.example.registrationdemo.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(timeout = 100)
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional(timeout = 100)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(timeout = 100)
    public void createUser(User user) {

        if (this.findUserByEmail(user.getEmail()).isPresent()) {
        	// TODO 例外処理の実装
            throw new RuntimeException("本登録済み");
        }

        userRepository.save(user);

    }

}
