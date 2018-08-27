package com.example.registrationdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "token_id", nullable = false)
    private String tokenId;

    public ProvisionalUser of(String name, String password, String email, String tokenId) {
        return ProvisionalUser.builder()
            .name(name)
            .password(password)
            .email(email)
            .tokenId(tokenId)
            .build();
    }
}
