package com.example.registrationdemo.entity;

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
@Table(name = "user_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvisionalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "token_id", nullable = false, unique = true)
    private String tokenId;

    @OneToOne
    @JoinColumn(name="user_id", insertable=false, updatable=false)
    private User user;

    @OneToOne
    @JoinColumn(name="token_id", insertable=false, updatable=false)
    private Token token;

    public ProvisionalUser of(String userId, String tokenId) {
        return ProvisionalUser.builder()
            .userId(userId)
            .tokenId(tokenId)
            .build();
    }
}
