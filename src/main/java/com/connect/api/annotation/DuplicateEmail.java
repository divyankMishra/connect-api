package com.connect.api.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DuplicateEmailValidator.class)
public @interface DuplicateEmail {
    String message() default "This email already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
