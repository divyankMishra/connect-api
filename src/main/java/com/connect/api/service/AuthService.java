package com.connect.api.service;

import com.connect.api.dto.UserDto;
import com.connect.api.dto.payload.request.SignInDto;
import com.connect.api.dto.payload.request.SignUpDto;

public interface AuthService {
    boolean login(SignInDto signInDto);

    UserDto signup(SignUpDto signUpDto);
}
