package com.example.registrationdemo.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 仮登録ユーザークラス
@Entity
@Table(name = "provisional_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvisionalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    public static ProvisionalUser of(String email, String password, String token, LocalDateTime expireDate) {
        return ProvisionalUser.builder()
            .email(email)
            .password(password)
            .token(token)
            .expireDate(expireDate)
            .createDate(LocalDateTime.now())
            .build();
    }
}
