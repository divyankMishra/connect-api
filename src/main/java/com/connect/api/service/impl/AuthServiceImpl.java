package com.connect.api.service.impl;

import com.connect.api.dto.UserDto;
import com.connect.api.dto.payload.request.SignInDto;
import com.connect.api.dto.payload.request.SignUpDto;
import com.connect.api.model.User;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.AuthService;
import com.connect.api.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final CryptoUtil cryptoUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, CryptoUtil cryptoUtil) {
        this.userRepository = userRepository;
        this.cryptoUtil = cryptoUtil;
    }

    @Override
    public boolean login(SignInDto signInDto) {
        UserDetails userDetails = userRepository.findByUsername(signInDto.getUsername());
        return userDetails != null && cryptoUtil.matches(signInDto.getPassword(), userDetails.getPassword());
    }

    @Override
    public UserDto signup(SignUpDto signUpDto) {
        User user = User.builder()
                .createdAt(new Date())
                .updatedAt(new Date())
                .firstname(signUpDto.getFirstname())
                .lastname(signUpDto.getLastname())
                .username(signUpDto.getUsername())
                .password(cryptoUtil.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .build();
        User createdUser = userRepository.save(user);
        return new UserDto(createdUser);
    }
}
