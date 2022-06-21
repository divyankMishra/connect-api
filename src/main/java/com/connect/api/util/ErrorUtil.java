package com.connect.api.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorUtil {
    public static ResponseEntity<Object> bindingErrors(BindingResult bindingResult) {
        Map<String, List<String>> errors = new HashMap<>();
        if (bindingResult.hasGlobalErrors())
            errors.putAll(bindingResult.getGlobalErrors().stream().collect(Collectors.groupingBy(ObjectError::getCode, Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList()))));
        if (bindingResult.hasFieldErrors())
            errors.putAll(bindingResult.getFieldErrors().stream().collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList()))));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
