package com.connect.api.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPassword {
    public String message() default "Passwords should be same!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
