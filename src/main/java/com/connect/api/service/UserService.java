package com.connect.api.service;

import com.connect.api.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto findUserByEmail(String email);

    boolean existsByUsernameEquals(String username);

    boolean existsByEmailEqualsIgnoreCase(String email);

}
