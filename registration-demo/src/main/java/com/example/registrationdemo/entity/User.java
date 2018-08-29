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

// ユーザークラス
@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "name", nullable = true,  length = 128)
    private String name;
    @Column(name = "password", nullable = false, length = 256)
    private String password;
    @Column(name = "email", nullable = false, unique = true, length = 256)
    private String email;
    /*@Column(name = "status", nullable = false, length = 1)
    private Integer status;*/

    public static User of(String name, String password, String email) {
        return User.builder()
            .name(name)
            .password(password)
            .email(email)
            .build();
    }

    public static User of(ProvisionalUser user){
        return User.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}
