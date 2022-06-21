package com.connect.api.annotation;

import com.connect.api.dto.payload.request.SignUpDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null ? false : validate(value);
    }

    private boolean validate(Object value) {
        SignUpDto signUpDto = (SignUpDto) value;
        if (signUpDto.getPassword() == null
                || signUpDto.getConfirmPassword() == null
                || !signUpDto.getConfirmPassword().equals(signUpDto.getPassword())) {
            return false;
        }
        return true;
    }
}
