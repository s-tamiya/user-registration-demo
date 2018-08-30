package com.example.registrationdemo.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Component;

@Component
public class OneTimeToken {
    
    public static final Long EXPIRE_TIME = 24L;
    private final String algorithm = "SHA1PRNG";

    public String genarateToken() throws Exception {
        String token;
        
        try {
            SecureRandom random = SecureRandom.getInstance(algorithm);
            byte bytes[] = new byte[16];
            random.nextBytes(bytes);
            token = Base64.encodeBase64URLSafeString(bytes);
            
        } catch (NoSuchAlgorithmException | IllegalIdentifierException e) {
            e.printStackTrace();
            throw new Exception("ワンタイムトークン生成中のエラー");
        }
        
        return token;
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
}
