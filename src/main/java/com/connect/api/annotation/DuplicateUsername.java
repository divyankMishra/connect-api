package com.connect.api.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DuplicateUsernameValidator.class)
public @interface DuplicateUsername {
    public String message() default "This username already exists!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
