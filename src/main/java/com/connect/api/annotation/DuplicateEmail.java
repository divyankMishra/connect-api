package com.connect.api.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DuplicateEmailValidator.class)
public @interface DuplicateEmail {
    public String message() default "This email already exists!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
