package com.example.registrationdemo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.registrationdemo.entity.ProvisionalUser;
import com.example.registrationdemo.entity.User;
import com.example.registrationdemo.repository.ProvisionalUserRepository;
import com.example.registrationdemo.repository.UserRepository;
import com.example.registrationdemo.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    public static final Long ONE_TIME_TOKEN_EXPIRE_TIME = 24L;

    private ProvisionalUserRepository provisionalUserRepository;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
            ProvisionalUserRepository provisionalUserRepository,
            UserRepository userRepository)
    {
        this.provisionalUserRepository = provisionalUserRepository;
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
    @Transactional(readOnly = true)
    public Optional<ProvisionalUser> findProvisionalUserByEmail(String email) {
        return provisionalUserRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvisionalUser> findProvisionalUserByToken(String token) {
        return provisionalUserRepository.findByToken(token);
    }

    @Override
    @Transactional(timeout = 100)
    public void provisionalRegister(User user) {

        if (this.findUserByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("本登録済み");
        }

        ProvisionalUser entry = this.findProvisionalUserByEmail(user.getEmail())
            .orElse(ProvisionalUser.of(user.getEmail(), user.getPassword()));

        entry.setToken("test1");
        entry.setExpireDate(LocalDateTime.now().plusHours(ONE_TIME_TOKEN_EXPIRE_TIME));
        provisionalUserRepository.save(entry);

    }

    @Override
    @Transactional(timeout = 100)
    public void register(String token) {
        ProvisionalUser provUser = provisionalUserRepository.findByToken(token).orElseThrow(RuntimeException::new);
        if (provUser.isExpired()) {
            throw new RuntimeException("トークンの有効期限切れ");
        }

        User user = User.of(provUser);
        userRepository.save(user);
    }
}
