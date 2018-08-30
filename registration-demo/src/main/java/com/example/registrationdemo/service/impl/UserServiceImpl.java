package com.example.registrationdemo.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.registrationdemo.entity.ProvisionalUser;
import com.example.registrationdemo.entity.User;
import com.example.registrationdemo.repository.ProvisionalUserRepository;
import com.example.registrationdemo.repository.UserRepository;
import com.example.registrationdemo.security.OneTimeToken;
import com.example.registrationdemo.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private ProvisionalUserRepository provisionalUserRepository;
    private UserRepository userRepository;
    private OneTimeToken oneTimeToken;

    @Autowired
    public UserServiceImpl(
            ProvisionalUserRepository provisionalUserRepository,
            UserRepository userRepository,
            OneTimeToken oneTimeToken)
    {
        this.provisionalUserRepository = provisionalUserRepository;
        this.userRepository = userRepository;
        this.oneTimeToken= oneTimeToken;
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
    public void provisionalRegister(User entry) throws Exception {

        if (this.findUserByEmail(entry.getEmail()).isPresent()) {
        	// TODO 例外処理の実装
            throw new RuntimeException("本登録済み");
        }

        ProvisionalUser provUser = this.findProvisionalUserByEmail(entry.getEmail())
            .orElse(ProvisionalUser.of(entry.getEmail(), entry.getPassword()));
        
        String token;
        try {
            token = oneTimeToken.genarateToken();
        } catch (Exception e) {
            throw e;
        }
        provUser.setToken(token);
        provUser.setExpireDate(LocalDateTime.now().plusHours(OneTimeToken.EXPIRE_TIME));
        provisionalUserRepository.save(provUser);

    }

    @Override
    @Transactional(timeout = 100)
    public void register(String token) {
        
        ProvisionalUser provUser = provisionalUserRepository
                .findByToken(token)
                .orElseThrow(RuntimeException::new);// TODO 例外処理の実装

        if (provUser.isExpired()) {
        	// TODO 例外処理の実装
            throw new RuntimeException("URLの有効期限切れ");
        }

        User user = User.of(provUser);
        userRepository.save(user);
    }
    
}
