package com.connect.api.service.impl;

import com.connect.api.dto.UserDto;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null) throw new UsernameNotFoundException("User not found with username: " + username);
        return userDetails;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return new UserDto(userRepository.findByEmailIgnoreCase(email));
    }

    @Override
    public boolean existsByUsernameEquals(String username) {
        return userRepository.existsByUsernameEquals(username);
    }

    @Override
    public boolean existsByEmailEqualsIgnoreCase(String email) {
        return userRepository.existsByEmailEqualsIgnoreCase(email);
    }
}
