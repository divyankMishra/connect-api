package com.connect.api.annotation;

import com.connect.api.dto.payload.request.SignUpDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null && validate(value);
    }

    private boolean validate(Object value) {
        SignUpDto signUpDto = (SignUpDto) value;
        return null != signUpDto.getPassword()
                && signUpDto.getConfirmPassword() != null
                && signUpDto.getConfirmPassword().equals(signUpDto.getPassword());
    }
}
