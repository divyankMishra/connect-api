package com.connect.api.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DuplicateUsernameValidator.class)
public @interface DuplicateUsername {
    String message() default "This username already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
