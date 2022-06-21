package com.connect.api.filter;

import com.connect.api.model.User;
import com.connect.api.service.UserService;
import com.connect.api.util.Constants;
import com.connect.api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @Autowired
    public JWTAuthenticationFilter(JwtTokenUtil jwtTokenUtil,UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> cookie = Arrays.stream(cookies).filter(el -> el.getName().equals(Constants.JWT)).findFirst();
            if(cookie.isPresent()){
                String token = cookie.get().getValue();
                String username = jwtTokenUtil.getUsernameFromToken(token);
                UserDetails user = userService.loadUserByUsername(username);
                Boolean valid = jwtTokenUtil.validateToken(token,user);
                if(valid){
                    Authentication auth = new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Token Received!");
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().endsWith("/signup") || request.getServletPath().endsWith("/login");
    }
}
