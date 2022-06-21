package com.connect.api.annotation;

import com.connect.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DuplicateUsernameValidator implements ConstraintValidator<DuplicateUsername, String> {
    @Autowired
    UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.existsByUsernameEquals(value);
    }

}
