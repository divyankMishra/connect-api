package com.connect.api.controller;

import com.connect.api.dto.UserDto;
import com.connect.api.dto.payload.request.SignInDto;
import com.connect.api.dto.payload.request.SignUpDto;
import com.connect.api.model.User;
import com.connect.api.service.AuthService;
import com.connect.api.service.UserService;
import com.connect.api.util.Constants;
import com.connect.api.util.ErrorUtil;
import com.connect.api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil, AuthService authService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody SignUpDto signup, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ErrorUtil.bindingErrors(bindingResult);
        }
        UserDto user = authService.signup(signup);
        String token = jwtTokenUtil.generateToken(userService.loadUserByUsername(user.getUsername()));
        setTokenCookie(response, token);
        return ResponseEntity.ok().body(user);
    }

    private void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(Constants.JWT, token);
        cookie.setMaxAge(300);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody SignInDto signInDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ErrorUtil.bindingErrors(bindingResult);
        }
        if (authService.login(signInDto)) {
            UserDetails userDetails = userService.loadUserByUsername(signInDto.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            setTokenCookie(response, token);
            return ResponseEntity.ok().body(new UserDto((User) userDetails));

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("""
                {
                  "message": "Username or Password is not correct!"
                }
                """);
    }

    @GetMapping("/other")
    public String other() {
        return """
                {
                "message":"This is new start."
                }
                """;
    }

}
