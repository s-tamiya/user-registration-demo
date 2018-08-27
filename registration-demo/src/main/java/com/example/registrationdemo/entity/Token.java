package com.example.registrationdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "token")
@Data
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "expire_date", nullable = false)
    private Date expireDate;

    public Token of(String token, Date expireDate) {
        return Token.builder()
            .token(token)
            .expireDate(expireDate)
            .build();
    }
}