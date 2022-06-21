package com.connect.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CryptoUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String dbPassword) {
       return passwordEncoder.matches(rawPassword,dbPassword);
    }
}
